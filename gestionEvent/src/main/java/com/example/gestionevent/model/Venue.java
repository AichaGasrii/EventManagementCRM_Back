package com.example.gestionevent.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Venue {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int venueId;
	private String venueName;
	private String venuePlace;
	private String venueContact;
	private String userName;
	private String imagePath;
	
	
	public Venue() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Venue(int venueId, String venueName, String venuePlace, String venueContact, String userName, String imagePath) {
		super();
		this.venueId = venueId;
		this.venueName = venueName;
		this.venuePlace = venuePlace;
		this.venueContact = venueContact;
		this.userName = userName;
		this.imagePath = imagePath;
	}
	public int getVenueId() {
		return venueId;
	}
	public void setVenueId(int venueId) {
		this.venueId = venueId;
	}
	public String getVenueName() {
		return venueName;
	}
	public void setVenueName(String venueName) {
		this.venueName = venueName;
	}
	public String getVenuePlace() {
		return venuePlace;
	}
	public void setVenuePlace(String venuePlace) {
		this.venuePlace = venuePlace;
	}
	public String getVenueContact() {
		return venueContact;
	}
	public void setVenueContact(String venueContact) {
		this.venueContact = venueContact;
	}
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	@Override
	public String toString() {
		return "Venue [venueId=" + venueId + ", venueName=" + venueName + ", venuePlace=" + venuePlace
				+ ", venueContact=" + venueContact + ", userName=" + userName + ", imagePath=" + imagePath +"]";
	}
	
	
	
	

}
