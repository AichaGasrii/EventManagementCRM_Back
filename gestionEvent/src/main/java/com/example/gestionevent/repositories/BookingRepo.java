package com.example.gestionevent.repositories;


import com.example.gestionevent.model.Booking;
import com.example.gestionevent.model.User;
import com.example.gestionevent.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepo extends JpaRepository<Booking, Integer> {

	public List<Booking> findByVenue(Venue venue);



	public List<Booking> findByUser(User user);
    List<Booking> findBookingByVenueAndPaymentStatusOrderByDate(Venue venue, String paymentStatus);

}
