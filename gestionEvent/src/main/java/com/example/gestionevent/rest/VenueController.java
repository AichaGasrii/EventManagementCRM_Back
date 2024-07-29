package com.example.gestionevent.rest;

import com.example.gestionevent.model.Venue;
import com.example.gestionevent.services.NotificationService;
import com.example.gestionevent.services.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;



@RestController
@RequestMapping("/gestionEvent/venue")


public class VenueController {

	@Autowired
	VenueService venueService;

	@Autowired
	NotificationService notificationService;

	/// handler to get All venues by organizer Id
	@GetMapping("/organizer/{userName}")
	public List<Venue> getVenuesByOrganizerId(@PathVariable("userName")String userName) {
		return venueService.getVenuesByOrganizerId(userName);
	}

	/// handler to get All distinct places
	@GetMapping("/places")
	public List<String> getAllDistinctPlaces() {
		return venueService.getAllDistinctPlaces();
	}

	// handler to get venue for selected place
	@GetMapping("/getVenues/{place}")
	List<Venue> getVenueOfPlace(@PathVariable("place") String place) {
		return venueService.getVenueOfPlace(place);
	}

	// Add Venue Request Handler
	@PostMapping("/add")
	public Venue addVenue(
			@RequestParam("venueName") String venueName,
			@RequestParam("venuePlace") String venuePlace,
			@RequestParam("venueContact") String venueContact,
			@RequestParam("userName") String userName,
			@RequestParam("image") MultipartFile image) {
		// Add the venue first
		Venue savedVenue = venueService.addVenue(venueName, venuePlace, venueContact, userName, image);

		// Notify about the new venue
		notificationService.notifyOnVenueAdd(savedVenue);

		// Return the saved venue
		return savedVenue;
	}

	// Update Venue
	@PutMapping("/updateVenue")
	public Venue updateVenue(
			@RequestParam("venueId") int venueId,
			@RequestParam("venueName") String venueName,
			@RequestParam("venuePlace") String venuePlace,
			@RequestParam("venueContact") String venueContact,
			@RequestParam(value = "image", required = false) MultipartFile image) {
		return venueService.updateVenue(venueId, venueName, venuePlace, venueContact, image);
	}

	// Get venue by venue Id
	@GetMapping("/getVenue/{venueId}")
	public Venue getVenue(@PathVariable("venueId") int venueId) {
		return venueService.getVenue(venueId);
	}

	// List of venues
	@GetMapping("/getVenues")
	public List<Venue> getVenues() {
		return venueService.getVenues();
	}



	// Delete Venue
	@DeleteMapping("/deleteVenue/{venueId}")
	public int deleteVenue(@PathVariable("venueId") int venueId) {

		return venueService.deleteVenue(venueId);
	}


}
