package com.example.gestionauth.repositories;

import com.example.gestionauth.persistence.entity.Notification;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NotificationRepo extends CrudRepository<Notification, Integer> {

	List<Notification> findByUserName(String userName);

}
