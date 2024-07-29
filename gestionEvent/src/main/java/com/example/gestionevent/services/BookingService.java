package com.example.gestionevent.services;

import com.example.gestionevent.model.Booking;
import com.example.gestionevent.model.BookingDetail;

import java.sql.Date;
import java.util.List;



public interface BookingService {


	public List<BookingDetail> getBookingDetailByOrganizerId(String userName, String tense);


    public Boolean isAvailable(Booking tempBooking);
	public Boolean isAvailableForPayment(int tempBookingId);//**

	public Booking getBooking(int bookingId);

	public List<BookingDetail> getBookings();

	public List<BookingDetail> getBookingsByUserId(String userName);

	public BookingDetail bookingDetail(int bookingId);

	public int payment(int bookingId);//**

	public int deleteBooking(int bookingId);

	public int checkActiveBookings(int venueId);
    public List<String> getUpcomingBookedDates(int venueId);
    public Booking rectifyBooking(Booking localBooking);
    public List<Date> getAllUpcomingBookedDates();}
