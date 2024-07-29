package com.example.gestionevent.rest;

import com.example.gestionevent.model.Booking;
import com.example.gestionevent.model.BookingDetail;
import com.example.gestionevent.services.BookingService;
import com.example.gestionevent.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/gestionEvent/booking")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @Autowired
    NotificationService notificationService;



    // Add a logger
    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);

    //Handler to add booking
    @PostMapping("/add")
    public ResponseEntity<Booking> addBooking(@RequestBody Booking booking) {
        if (this.bookingService.isAvailable(booking)) {
            Booking createdBooking = this.bookingService.rectifyBooking(booking);
            this.notificationService.notifyOnBooking(createdBooking);
            return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // Handler to get Booking data by booking ID
    @GetMapping("/getBooking/{bookingId}")
    public Booking getBooking(@PathVariable("bookingId") int bookingId) {
        return bookingService.getBooking(bookingId);
    }

    // Handler to get list of all bookings
    @GetMapping("/getAllBookings")
    public List<BookingDetail> getBookings() {
        return bookingService.getBookings();
    }

    // Handler to get list of bookings for USER
    @GetMapping("/bookingsByUserId/{userName}")
    public List<BookingDetail> getBookingsByUserId(@PathVariable("userName") String userName) {
        return bookingService.getBookingsByUserId(userName);
    }

    // Handler to get Booking Detail by Booking ID
    @GetMapping("/bookingDetail/{bookingId}")
    public BookingDetail bookingDetail(@PathVariable("bookingId") int bookingId) {
        return bookingService.bookingDetail(bookingId);
    }

    // Handler to Make payment request
    @GetMapping("/doPayment/{bookingId}")
    public int payment(@PathVariable("bookingId") int bookingId) {

        // Check if the booking for which payment is being made is already booked by any other user
        // If that booking is available for payment then transfer request to add payment data and return 1
        // Generate notification for the Payment.
        if (this.bookingService.isAvailableForPayment(bookingId)) {
            this.bookingService.payment(bookingId);
            this.notificationService.notifyOnPayment(bookingId);
            return 1;
        } else {
            return 0;
        }
    }

    // Request handler to delete Booking
    @DeleteMapping("/deleteBooking/{bookingId}")
    public int deleteBooking(@PathVariable("bookingId") int bookingId) {
        return bookingService.deleteBooking(bookingId);
    }

    // Request handler to get List of Upcoming Bookings only
    @GetMapping("/upcomingBookings/{userName}")
    public List<BookingDetail> getBookingsByOrgId(@PathVariable("userName") String userName) {
        return bookingService.getBookingDetailByOrganizerId(userName,"Future");
    }

    // Request handler to get Previous bookings only
    @GetMapping("/previousBookings/{userName}")
    public List<BookingDetail> getPreviousBookingsByOrgId(@PathVariable("userName") String userName) {
        return bookingService.getBookingDetailByOrganizerId(userName,"Past");
    }


    @GetMapping("/getDates/{venueId}")
    public List<String> getDates(@PathVariable("venueId") int venueId) {
        return bookingService.getUpcomingBookedDates(venueId);
    }

    @GetMapping("/checkActiveBookings/{venueId}")
    public int checkActiveBookings(@PathVariable("venueId") int venueId) {
        return bookingService.checkActiveBookings(venueId);
    }

    @GetMapping("/bookedDates/{venueId}")
    public List<String> getBookedDates(@PathVariable("venueId") int venueId) {
        return bookingService.getUpcomingBookedDates(venueId);
    }
    @GetMapping("/bookings/bookedDates")
    public List<Date> getAllBookedDates() {
        return bookingService.getAllUpcomingBookedDates();
    }


}
