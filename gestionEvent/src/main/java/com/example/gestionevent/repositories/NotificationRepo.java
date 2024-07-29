package com.example.gestionevent.repositories;

import com.example.gestionevent.model.Notification;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NotificationRepo extends CrudRepository<Notification, Integer> {

	List<Notification> findByUserName(String userName);

}
