package com.fosterpet.backend.payment;

import com.fosterpet.backend.booking.Booking;
import com.fosterpet.backend.booking.BookingRepository;
import com.fosterpet.backend.user.User;
import com.fosterpet.backend.user.UserRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.EphemeralKey;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.net.RequestOptions;
import com.stripe.param.CustomerCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Override
    public Payment createPayment(String bookingId) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));
        User user = userRepository.findById(booking.getOwner().getUserId()).orElseThrow(() -> new RuntimeException("User not found"));

        Payment payment = Payment.builder()
                .amount(booking.getTotal())
                .currency("lkr")
                .customerName(user.getFirstName() + " " + user.getLastName())
                .customerEmail(user.getEmail())
                .customerID(user.getUserId())
                .build();

        return payment;

    }

    @Override
    public String createStripeCustomer(String email, String name, String id) throws StripeException {
        Stripe.apiKey = stripeApiKey;

        CustomerCreateParams customerParams =
                CustomerCreateParams.builder()
                        .setEmail(email)
                        .setName(name)
                        .setDescription("UserId: " + id)
                        .build();

        Customer customer = Customer.create(customerParams);

        return customer.getId();
    }

    @Override
    public Map<String, String> createPaymentIntent(String bookingId) throws StripeException {
        Stripe.apiKey = stripeApiKey;
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));

        Payment payment = createPayment(bookingId);
        User user = userRepository.findById(payment.getCustomerID()).orElseThrow(() -> new RuntimeException("User not found"));

        Customer customer = Customer.retrieve(user.getStripeCustomerId());

        PaymentIntent paymentIntent;

        if (booking.getPaymentIntentId() != null) {
            paymentIntent = PaymentIntent.retrieve(booking.getPaymentIntentId());
        }
        else {
            // Create a PaymentIntent with the order amount and currency
            Map<String, Object> params = new HashMap<>();
            params.put("amount", Math.round(payment.getAmount() * 100)); // amount in cents
            params.put("currency", payment.getCurrency());
            params.put("payment_method_types", List.of("card"));
            params.put("customer", customer.getId());
            params.put("description", "BookingId: " + bookingId);

            paymentIntent = PaymentIntent.create(params);
        }

            RequestOptions requestOptions = RequestOptions.builder()
                    .setStripeVersionOverride("2024-04-10")
                    .setApiKey(stripeApiKey)
                    .build();

            // Create an ephemeral key for the customer
            Map<String, Object> ephemeralKeyParams = new HashMap<>();
            ephemeralKeyParams.put("customer", paymentIntent.getCustomer());

            EphemeralKey ephemeralKey = EphemeralKey.create(ephemeralKeyParams, requestOptions);

            Map<String, String> responseData = new HashMap<>();
            responseData.put("paymentIntent", paymentIntent.getClientSecret());
            responseData.put("ephemeralKey", ephemeralKey.getSecret());
            responseData.put("customer", paymentIntent.getCustomer());

            return responseData;
    }

    @Override
    public Invoice createPaymentInvoice(String paymentIntentId) throws StripeException {
        Stripe.apiKey = stripeApiKey;

        PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);

        PaymentMethod paymentMethod = PaymentMethod.retrieve(paymentIntent.getPaymentMethod());

        String prefix = "BookingId: ";
        String bookingId = paymentIntent.getDescription().substring(prefix.length());

        Invoice invoice = Invoice.builder()
                .paymentIntentId(paymentIntent.getId())
                .booking(bookingRepository.findByBookingID(bookingId))
                .status(paymentIntent.getStatus())
                .paymentMethod(paymentMethod.getType())
                .paymentMethodBrand(paymentMethod.getCard().getBrand())
                .paymentMethodLast4(paymentMethod.getCard().getLast4())
                .paymentMethodExpMonth(paymentMethod.getCard().getExpMonth().toString())
                .paymentMethodExpYear(paymentMethod.getCard().getExpYear().toString())
                .amount(paymentIntent.getAmount()/100)
                .currency(paymentIntent.getCurrency())
                .createdAt(Instant.now())
                .paymentDate(Instant.ofEpochSecond(paymentIntent.getCreated()))
                .build();

        var saved = invoiceRepository.save(invoice);

        return saved;
    }

    @Override
    public Invoice getPaymentInvoice(String invoiceId) {
        return invoiceRepository.findById(invoiceId).orElseThrow(() -> new RuntimeException("Invoice not found"));
    }

    @Override
    public PaymentIntent getPaymentIntent(String paymentIntentId) throws StripeException {
        Stripe.apiKey = stripeApiKey;

        return PaymentIntent.retrieve(paymentIntentId);
    }

    @Override
    public List<Invoice> getPaymentInvoicesByUserId(String userId) {
        try {
            List<Booking> bookings = bookingRepository.findByOwnerUserId(userId);
            List<Invoice> invoices= new java.util.ArrayList<>();
            for (Booking booking : bookings) {
                Invoice invoice = invoiceRepository.findByBookingBookingID(booking.getBookingID());
                if (invoice != null) invoices.add(invoice);
            }
            return invoices;
        } catch (Exception e) {
            throw new RuntimeException("Invoices not found");
        }
    }

    @Override
    public List<Invoice> getPaymentInvoicesByKennelId(String kennelId) {
        try {
            List<Booking> bookings = bookingRepository.findByKennelKennelID(kennelId);
            List<Invoice> invoices= new java.util.ArrayList<>();
            for (Booking booking : bookings) {
                Invoice invoice = invoiceRepository.findByBookingBookingID(booking.getBookingID());
                if (invoice != null) invoices.add(invoice);
            }
            return invoices;
        } catch (Exception e) {
            throw new RuntimeException("Invoices not found");
        }
    }

    @Override
    public List<Invoice> getPaymentInvoicesByVolunteerId(String volunteerId) {
        try {
            List<Booking> bookings = bookingRepository.findByVolunteerVolunteerId(volunteerId);
            List<Invoice> invoices= new java.util.ArrayList<>();
            for (Booking booking : bookings) {
                Invoice invoice = invoiceRepository.findByBookingBookingID(booking.getBookingID());
                if (invoice != null) invoices.add(invoice);
            }
            return invoices;
        } catch (Exception e) {
            throw new RuntimeException("Invoices not found");
        }
    }

    @Override
    public Invoice getPaymentInvoiceByBookingId(String bookingId) {
        return invoiceRepository.findByBookingBookingID(bookingId);
    }


}
