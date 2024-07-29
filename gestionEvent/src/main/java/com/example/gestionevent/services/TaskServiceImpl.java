package com.example.gestionevent.services;

import com.example.gestionevent.exception.ResourceNotFoundException;
import com.example.gestionevent.model.Booking;
import com.example.gestionevent.model.Task;
import com.example.gestionevent.repositories.BookingRepo;
import com.example.gestionevent.repositories.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@org.springframework.stereotype.Service
public class TaskServiceImpl implements TaskService{

  @Autowired
  private TaskRepo taskRepo ;
  @Autowired
  private BookingRepo bookingRepo ;

    public List<Task> getTasksByBookingId(Integer bookingId) {
        return taskRepo.findByBookingBookingId(bookingId);
    }


    @Override
    public Task createTask(Integer bookingId, Task task) throws ResourceNotFoundException {
        Booking booking = bookingRepo.findById(bookingId)
            .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id " + bookingId));
        task.setBooking(booking);
        return taskRepo.save(task);
    }
    public void deleteTask(Integer id) {
        taskRepo.deleteById(id);
    }
    public Task updateTask(Integer taskId, Task taskDetails) throws ResourceNotFoundException {
        Task task = taskRepo.findById(taskId)
            .orElseThrow(() -> new ResourceNotFoundException("Task not found with id " + taskId));

        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setStatus(taskDetails.getStatus());

        return taskRepo.save(task);
    }
}
