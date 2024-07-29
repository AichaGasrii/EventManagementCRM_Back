package com.example.gestionevent.services;

import com.example.gestionevent.model.Booking;
import com.example.gestionevent.model.Notification;
import com.example.gestionevent.model.User;
import com.example.gestionevent.model.Venue;

import java.util.List;

public interface NotificationService {

	int updateNotificationCheckedStatus(Notification notification);

	public int notifyOnPayment(int bookingId);

	public int notifyOnBooking(Booking booking);
	
	public int notifyOnVenueAdd(Venue venue);

	public int deleteNotification(int notificationId);

	public List<Notification> getNotifications(String userName);
	
}
