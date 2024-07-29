package com.example.gestionevent.rest;

import com.example.gestionevent.model.Event;
import com.example.gestionevent.services.EventService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/gestionEvent/event")


public class EventController {

	@Autowired
	EventService eventService;

	// Get List of events by venueId
	@GetMapping("/getEvents/{venueId}")
    public List<Event> getEventsByVenueId(@PathVariable("venueId") int eventId) {
    	 return eventService.getEventsByVenueId(eventId);
    }

	// Add event
	@PostMapping("/add")
	public Event addEvent(@RequestBody Event event) {
		return this.eventService.addEvent(event);
	}

	// Get event by Name and VenueId
	@GetMapping("/getOne/{eventName}/{venueId}")
	public Event getByNameAndVenueId(@PathVariable("eventName") String EName,@PathVariable("venueId") int VId) {
		return this.eventService.getByNameAndVenueId(EName,VId);
	}

	// Get event by Event Id
	@GetMapping("/getEvent/{eventId}")
    public Event getEvent(@PathVariable("eventId") int eventId) {
    	 return eventService.getEvent(eventId);
    }

    //Update event
    @PutMapping("/updateEvent")
    public int updateEvent(@RequestBody Event event) {
   	 return eventService.updateEvent(event);
   }

    //Delete event
    @DeleteMapping("/deleteEvent/{eventId}")
    public int deleteEvent(@PathVariable("eventId") int eventId) {
    	return eventService.deleteEvent(eventId);
      }


}
