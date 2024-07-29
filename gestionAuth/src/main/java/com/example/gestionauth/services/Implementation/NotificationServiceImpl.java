package com.example.gestionauth.services.Implementation;

import com.example.gestionauth.persistence.entity.Notification;
import com.example.gestionauth.persistence.entity.User;
import com.example.gestionauth.repositories.NotificationRepo;
import com.example.gestionauth.repositories.UserRepository;
import com.example.gestionauth.services.Interface.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@org.springframework.stereotype.Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	UserRepository userRepo;

	@Autowired
	NotificationRepo notificationRepo;


	// Generate Notification On Registration//##
	@Override
	public int notificationOnRegistration(User user) {
		User newUser = userRepo.findByUserEmail(user.getUserEmail());

		Notification notification = new Notification();

		notification.setUserName(newUser.getUserName());
		LocalDate localDate = LocalDate.now();
		String localTime = LocalTime.now().truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_LOCAL_TIME);
		notification.setDate(localDate.toString());
		notification.setTime(localTime);
		if (newUser.getRole().equals("user")) {
			String userNoti = "Welcome " + newUser.getUserFirstName()
					+ ", You have successfully registered on EVENTFLOW Online Event Management CRM. "
					+ "Now you can book your events on various venues.";
			notification.setMessage(userNoti);
		} else {
			String orgNoti = "Welcome " + user.getUserFirstName()
					+ ", You have successfully registered on EVENTFLOW Online Event Management CRM. "
					+ "Now you can add your venues on the portal";
			notification.setMessage(orgNoti);
		}

		notificationRepo.save(notification);
		return 1;
	}



}
