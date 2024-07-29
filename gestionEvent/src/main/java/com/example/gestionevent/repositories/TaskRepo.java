package com.example.gestionevent.repositories;

import com.example.gestionevent.model.Booking;
import com.example.gestionevent.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepo  extends JpaRepository<Task, Integer> {
    List<Task> findByBookingBookingId(Integer bookingId);

}
