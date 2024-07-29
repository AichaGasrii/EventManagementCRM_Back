package com.example.gestionevent.services;

import com.example.gestionevent.model.*;
import com.example.gestionevent.repositories.NotificationRepo;
import com.example.gestionevent.repositories.UserRepository;
import com.example.gestionevent.repositories.VenueRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	UserRepository userRepo;

	@Autowired
	NotificationRepo notificationRepo;

	@Autowired
	BookingService bookingService;

	@Autowired
	VenueRepo venueRepo;


	@Override
	public int updateNotificationCheckedStatus(Notification notification) {
		Optional<Notification> existingNotificationOptional = notificationRepo.findById(notification.getNotificationId());
		if (existingNotificationOptional.isPresent()) {
			Notification existingNotification = existingNotificationOptional.get();
			// Update the checked status
			existingNotification.setChecked(notification.isChecked());
			// Save the updated notification
			notificationRepo.save(existingNotification);
			return 1; // Successful update
		} else {
			return 0; // Notification not found
		}
	}

	// Generate notification On Booking//##
	@Override
	public int notifyOnBooking(Booking booking) {
		Notification notification = new Notification();

		notification.setUserName(booking.getUserName());
		LocalDate localDate = LocalDate.now();
		String localTime = LocalTime.now().truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_LOCAL_TIME);

		notification.setDate(localDate.toString());
		notification.setTime(localTime);

		String userNoti = "Dear User, Your booking data is stored temporary, Please make it fixed by completing payment";

		notification.setMessage(userNoti);
		notificationRepo.save(notification);
		return 1;

	}

	// Generate notification On payment//##
	@Override
	public int notifyOnPayment(int bookingId) {

		BookingDetail singleBooking = bookingService.bookingDetail(bookingId);

		String userName = venueRepo.findById(singleBooking.getVenueId()).get().getUserName();

		User user = userRepo.findUserByUserName(userName).get();

		String userNoti = "Dear " + singleBooking.getFirstName() + ", Your payment of Rs. "
				+ singleBooking.getTotalCost() + " for booking ID " + singleBooking.getBookingId()
				+ " is processed successfully." + "Your " + singleBooking.getEventName()
				+ " event is booked on Venue Name " + singleBooking.getVenueName() + " at "
				+ singleBooking.getVenuePlace() + " on date " + singleBooking.getDate();

		String orgNoti = "Dear " + user.getUserFirstName() + ", Your venue " + singleBooking.getVenueName()
				+ " is booked by user " + singleBooking.getFirstName() + " " + singleBooking.getLastName()
				+ " for an event of " + singleBooking.getEventName() + " on date " + singleBooking.getDate();

		Notification notification = new Notification();
		notification.setUserName(singleBooking.getUserName());
		LocalDate localDate = LocalDate.now();
		String localTime = LocalTime.now().truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_LOCAL_TIME);

		notification.setDate(localDate.toString());
		notification.setTime(localTime);
		notification.setMessage(userNoti);

		notificationRepo.save(notification);

		Notification notification2 = new Notification();
		notification2.setUserName(userName);
		LocalDate localDate2 = LocalDate.now();
		String localTime2 = LocalTime.now().truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_LOCAL_TIME);

		notification2.setDate(localDate2.toString());
		notification2.setTime(localTime2);
		notification2.setMessage(orgNoti);
		notificationRepo.save(notification2);
		return 1;

	}

	// Genetate notification on New Venue Add//##
	// Genetate notification on New Venue Add//##
	@Override
	public int notifyOnVenueAdd(Venue venue) {

		User user = userRepo.findById(venue.getUserName()).get();

		String orgNoti = "Dear " + user.getUserFirstName() + " Your venue with name " + venue.getVenueName()
				+ " at place " + venue.getVenuePlace() + " is Successfully added. ";

		Notification notification = new Notification();
		notification.setUserName(venue.getUserName());
		LocalDate localDate = LocalDate.now();
		String localTime = LocalTime.now().truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_LOCAL_TIME);
		notification.setDate(localDate.toString());
		notification.setTime(localTime);
		notification.setMessage(orgNoti);
		notificationRepo.save(notification);
		return 1;

	}

	// Get List of Notifications
	@Override
	public List<Notification> getNotifications(String userName) {
		return notificationRepo.findByUserName(userName);
	}

	// Delete Notifications
	@Override
	public int deleteNotification(int notificationId) {
		notificationRepo.deleteById(notificationId);
		return 1;
	}

}
