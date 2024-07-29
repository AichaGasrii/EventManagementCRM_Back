package com.example.gestionevent.services;

import com.example.gestionevent.exception.ResourceNotFoundException;
import com.example.gestionevent.model.Task;

import java.util.List;

public interface TaskService {
     List<Task> getTasksByBookingId(Integer bookingId);
     Task createTask(Integer bookingId, Task task) throws ResourceNotFoundException;
     void deleteTask(Integer id);
    Task updateTask(Integer taskId, Task taskDetails)throws ResourceNotFoundException;
}
