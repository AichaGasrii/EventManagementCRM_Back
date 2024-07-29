package com.example.gestionevent.rest;

import com.example.gestionevent.services.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/gestionEvent/payment")

public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/charge")
    public ResponseEntity<String> charge(@RequestBody Map<String, Object> request) {
        String token = (String) request.get("token");
        Object amountObject = request.get("amount");
        String bookingIdString = (String) request.get("bookingId");

        int bookingId = Integer.parseInt(bookingIdString); // Convert String to Integer

        double amount;
        if (amountObject instanceof Integer) {
            amount = ((Integer) amountObject).doubleValue();
        } else {
            amount = (double) amountObject;
        }

        try {
            Charge charge = paymentService.createCharge(token, amount);
            paymentService.updatePaymentStatus(bookingId);
            return ResponseEntity.ok("Charge created: " + charge.getId());
        } catch (StripeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create charge");
        }
    }

}

