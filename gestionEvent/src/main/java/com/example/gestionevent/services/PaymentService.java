package com.example.gestionevent.services;
import com.example.gestionevent.model.Booking;
import com.example.gestionevent.repositories.BookingRepo;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentService {
    @Autowired
    BookingRepo bookingRepo;

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeApiKey;
    }

    public Charge createCharge(String token, double amount) throws StripeException {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", (int) (amount * 100));
        chargeParams.put("currency", "usd");
        chargeParams.put("source", token);

        return Charge.create(chargeParams);
    }
    public void updatePaymentStatus(Integer bookingId) {
        Booking booking = bookingRepo.findById(bookingId).orElseThrow(() -> new IllegalArgumentException("Invalid booking ID"));
        booking.setPaymentStatus("Processed");
        bookingRepo.save(booking);
    }
}
