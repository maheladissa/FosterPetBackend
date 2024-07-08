package com.fosterpet.backend.payment;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import jdk.dynalink.linker.LinkerServices;

import java.util.List;
import java.util.Map;

public interface PaymentService {
    Payment createPayment(String bookingId);

    Map<String, String> createPaymentIntent(String bookingId) throws StripeException;

    String createStripeCustomer(String email, String name, String id) throws StripeException;

    Invoice createPaymentInvoice(String paymentIntentId) throws StripeException;

    Invoice getPaymentInvoice(String invoiceId);

    List<Invoice> getPaymentInvoicesByUserId(String userId);

    List<Invoice> getPaymentInvoicesByKennelId(String kennelId);

    List<Invoice> getPaymentInvoicesByVolunteerId(String volunteerId);

    Invoice getPaymentInvoiceByBookingId(String bookingId);

    PaymentIntent getPaymentIntent(String paymentIntentId) throws StripeException;

}
