package com.example.gestionevent.services;

import com.example.gestionevent.model.Event;
import com.example.gestionevent.repositories.EventRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class EventServiceImpl implements EventService {

	@Autowired
	EventRepo eventRepo;

	// Get event list by venue Id
	@Override
	public List<Event> getEventsByVenueId(int venueId) {
		return eventRepo.findByVenueId(venueId);
	}

	// Add Event
	@Override
	public Event addEvent(Event event) {
		return eventRepo.save(event);
	}

	// Get event list by Name and Venue Id
	@Override
	public Event getByNameAndVenueId(String EName, int VId) {
		return eventRepo.findByEventNameAndVenueId(EName, VId);

	}

	// Get event By Id
	@Override
	public Event getEvent(int eventId) {
		return eventRepo.findById(eventId).get();
	}

	// Update Event
	@Override
	public int updateEvent(Event event) {
		Event event2 = eventRepo.findById(event.getEventId()).get();
		event2.setEventCost(event.getEventCost());
		event2.setEventName(event.getEventName());
		event2.setVenueId(event.getVenueId());
		event2.setNumberOfGuests(event.getNumberOfGuests()); // Update new field
		eventRepo.save(event2);
		return 1;
	}

	// Delete Event
	@Override
	public int deleteEvent(int eventId) {
		eventRepo.deleteById(eventId);
		return 1;
	}

}
