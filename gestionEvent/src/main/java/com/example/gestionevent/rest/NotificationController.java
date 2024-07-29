package com.example.gestionevent.rest;

import com.example.gestionevent.model.Notification;
import com.example.gestionevent.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/gestionEvent/notification")


public class NotificationController {

	@Autowired
	NotificationService notificationService;

	@PutMapping("/updateNotificationCheckedStatus")
	public ResponseEntity<String> updateNotificationCheckedStatus(@RequestBody Notification notification) {
		int result = notificationService.updateNotificationCheckedStatus(notification);
		if (result == 1) {
			return ResponseEntity.ok("Notification status updated successfully");
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	// Get Notification...
	@GetMapping("/getNotification/{userName}")
	public List<Notification> getNotifications(@PathVariable("userName") String userName) {
		return notificationService.getNotifications(userName);
	}

	// Delete Notifications
	@DeleteMapping("/deleteNotification/{notificationId}")
	public int deleteNotification(@PathVariable("notificationId") int notificationId) {
		return notificationService.deleteNotification(notificationId);
	}

}
