package com.example.gestionevent.services;

import com.example.gestionevent.model.*;
import com.example.gestionevent.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class VenueServiceImpl implements VenueService {

	@Autowired
	VenueRepo venueRepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	EquipmentRepo equipmentRepo;

	@Autowired
	FoodItemRepo foodItemRepo;

	@Autowired
	EventRepo eventRepo;

	@Value("${file.upload}")
	private String pathFile;


	@Override
	public boolean addFile(MultipartFile file) {
		try {
			File convertFile = new File(pathFile + file.getOriginalFilename());
			convertFile.createNewFile();
			FileOutputStream fout = new FileOutputStream(convertFile);
			fout.write(file.getBytes());
			fout.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	//add venue
	@Override
	public Venue addVenue(String venueName, String venuePlace, String venueContact, String userName, MultipartFile image) {
		boolean fileAdded = addFile(image);
		if (!fileAdded) {
			throw new RuntimeException("Error saving the image.");
		}
		String imagePath = pathFile + image.getOriginalFilename();
		Venue venue = new Venue();
		venue.setVenueName(venueName);
		venue.setVenuePlace(venuePlace);
		venue.setVenueContact(venueContact);
		venue.setUserName(userName);
		venue.setImagePath(imagePath);
		return venueRepo.save(venue);
	}

	//Update
	@Override
	public Venue updateVenue(int venueId, String venueName, String venuePlace, String venueContact, MultipartFile image) {
		System.out.println("Update Venue called with venueId: " + venueId);
		Venue venue = venueRepo.findById(venueId)
				.orElseThrow(() -> new RuntimeException("Venue not found with id: " + venueId));

		boolean fileAdded = true;
		String imagePath = venue.getImagePath();

		if (image != null && !image.isEmpty()) {
			fileAdded = addFile(image);
			imagePath = pathFile + image.getOriginalFilename();
			System.out.println("Image path set to: " + imagePath);
		}

		if (!fileAdded) {
			throw new RuntimeException("Error updating the image.");
		}

		venue.setVenueName(venueName);
		venue.setVenuePlace(venuePlace);
		venue.setVenueContact(venueContact);
		venue.setImagePath(imagePath);

		Venue updatedVenue = venueRepo.save(venue);
		System.out.println("Venue updated: " + updatedVenue);
		return updatedVenue;
	}
	// List of Users


	@Override
	public List<Venue> getVenuesByOrganizerId(String userName) {
		return venueRepo.findByUserName(userName);
	}

	// Get Distinct Places
	@Override
	public List<String> getAllDistinctPlaces() {
		List<Venue> venues = (List<Venue>) venueRepo.findAll();
		Set<String> venueSet = venues.stream().map(v -> v.getVenuePlace()).collect(Collectors.toSet());
		List<String> venueList = venueSet.stream().collect(Collectors.toList());
		return venueList;
	}

	// Venues by Place
	@Override
	public List<Venue> getVenueOfPlace(String place) {
		return venueRepo.findByVenuePlace(place);
	}


	// Get Venue by Id
	@Override
	public Venue getVenue(int venueId) {
		return venueRepo.findById(venueId).get();
	}

	// List all venues
	@Override
	public List<Venue> getVenues() {
		return (List<Venue>) venueRepo.findAll();
	}



	// Delete...
	@Override
	public int deleteVenue(int venueId) {

		List<FoodItem> foodItems = foodItemRepo.findByVenueId(venueId);

		for (FoodItem f : foodItems) {
			foodItemRepo.delete(f);
		}

		List<Event> events = eventRepo.findByVenueId(venueId);
		for (Event e : events) {
			eventRepo.delete(e);
		}

		List<Equipment> equips = equipmentRepo.findByVenueId(venueId);
		for (Equipment eq : equips) {
			equipmentRepo.delete(eq);
		}
		venueRepo.deleteById(venueId);
		return 1;
	}

}
