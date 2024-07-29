package com.example.gestionevent.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String description;
    private String status;

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;}
