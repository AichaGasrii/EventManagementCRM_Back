package com.example.gestionevent.services;

import com.example.gestionevent.model.User;
import com.example.gestionevent.model.Venue;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;


public interface VenueService {

	public List<Venue> getVenuesByOrganizerId(String userName);

	public List<String> getAllDistinctPlaces();

	public List<Venue> getVenueOfPlace(String place);

	public boolean addFile(MultipartFile file);
	public Venue addVenue(String venueName, String venuePlace, String venueContact, String userName, MultipartFile image);

	public Venue updateVenue(int venueId, String venueName, String venuePlace, String venueContact, MultipartFile image);

	public Venue getVenue(int venueId);

	public List<Venue> getVenues();


	public int deleteVenue(int venueId);


}
