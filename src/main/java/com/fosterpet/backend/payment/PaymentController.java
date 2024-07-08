package com.fosterpet.backend.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create-payment-intent")
    public ResponseEntity<?> createPaymentIntent() {
        try {
            return ResponseEntity.ok(paymentService.createPaymentIntent("6643b0229d659c135875a786"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get-payment-intent")
    public ResponseEntity<?> getPaymentIntent(@RequestParam String paymentIntentId) {
        try {
            return ResponseEntity.ok(paymentService.getPaymentIntent(paymentIntentId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/create-invoice")
    public ResponseEntity<?> createInvoice(@RequestParam String paymentIntentId) {
        try {
            return ResponseEntity.ok(paymentService.createPaymentInvoice(paymentIntentId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get-invoice")
    public ResponseEntity<?> getInvoice(@RequestParam String invoiceId) {
        try {
            return ResponseEntity.ok(paymentService.getPaymentInvoice(invoiceId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get-invoices-by-user")
    public ResponseEntity<?> getInvoicesByUser(@RequestParam String userId) {
        try {
            return ResponseEntity.ok(paymentService.getPaymentInvoicesByUserId(userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get-invoices-by-kennel")
    public ResponseEntity<?> getInvoicesByKennel(@RequestParam String kennelId) {
        try {
            return ResponseEntity.ok(paymentService.getPaymentInvoicesByKennelId(kennelId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
