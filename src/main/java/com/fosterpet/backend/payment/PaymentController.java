package com.fosterpet.backend.payment;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.EphemeralKey;
import com.stripe.model.PaymentIntent;
import com.stripe.net.ApiResource;
import com.stripe.net.RequestOptions;
import com.stripe.param.CustomerCreateParams;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin
public class PaymentController {

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @PostMapping("/create-payment-intent")
    public ResponseEntity<?> createPaymentIntent() throws StripeException {
        System.out.println("Creating Payment Intent");
        try {
            Stripe.apiKey = stripeApiKey;

            CustomerCreateParams customerParams =
                    CustomerCreateParams.builder()
                            .setEmail("abc@gmail.com")
                            .setName("John Doe")
                            .setDescription("Customer for John Doe")
                            .build();

            Customer customer = Customer.create(customerParams);

            // Create a PaymentIntent with the order amount and currency
            Map<String, Object> params = new HashMap<>();
            params.put("amount", 1099); // amount in cents
            params.put("currency", "usd");
            params.put("payment_method_types", List.of("card"));
            params.put("customer", customer.getId());


            PaymentIntent paymentIntent = PaymentIntent.create(params);

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

            return ResponseEntity.ok(responseData);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
