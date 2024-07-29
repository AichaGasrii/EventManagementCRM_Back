package com.example.gestionevent.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int eventId;
	private String eventName;
	private int eventCost;
	private int venueId;
	private int numberOfGuests; // New field

	public Event(int eventId, String eventName, int eventCost, int venueId, int numberOfGuests) {
		super();
		this.eventId = eventId;
		this.eventName = eventName;
		this.eventCost = eventCost;
		this.venueId = venueId;
		this.numberOfGuests = numberOfGuests; // Initialize new field
	}

	public Event() {
		super();
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public int getEventCost() {
		return eventCost;
	}

	public void setEventCost(int eventCost) {
		this.eventCost = eventCost;
	}

	public int getVenueId() {
		return venueId;
	}

	public void setVenueId(int venueId) {
		this.venueId = venueId;
	}

	public int getNumberOfGuests() {
		return numberOfGuests;
	}

	public void setNumberOfGuests(int numberOfGuests) {
		this.numberOfGuests = numberOfGuests;
	}

	@Override
	public String toString() {
		return "Event [eventId=" + eventId + ", eventName=" + eventName + ", eventCost=" + eventCost + ", venueId="
				+ venueId + ", numberOfGuests=" + numberOfGuests + "]"; // Include new field in toString
	}
}
