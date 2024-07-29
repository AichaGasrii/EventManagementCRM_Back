package com.example.gestionevent.rest;

import com.example.gestionevent.exception.ResourceNotFoundException;
import com.example.gestionevent.model.Task;
import com.example.gestionevent.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/gestionEvent/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping("/booking/{bookingId}")
    public List<Task> getTasksByBookingId(@PathVariable Integer bookingId) {
        return taskService.getTasksByBookingId(bookingId);
    }

    @PostMapping("/booking/{bookingId}")
    public ResponseEntity<Task> createTask(@PathVariable Integer bookingId, @RequestBody Task task) throws ResourceNotFoundException {
        return new ResponseEntity<>(taskService.createTask(bookingId, task), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Integer id) {
        taskService.deleteTask(id);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Integer id, @RequestBody Task taskDetails) throws ResourceNotFoundException {
        Task updatedTask = taskService.updateTask(id, taskDetails);
        return ResponseEntity.ok(updatedTask);
    }
}
