 package com.example.gestionevent.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FoodItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int foodItemId;
	private String foodItemName;
	private int foodItemCost;
	private int venueId;

	private String imagePath;
	
	public FoodItem(int foodItemId, String foodItemName, int foodItemCost, int venueId, String imagePath) {
		super();
		this.foodItemId = foodItemId;
		this.foodItemName = foodItemName;
		this.foodItemCost = foodItemCost;
		this.venueId = venueId;
		this.imagePath = imagePath;
	}
	public FoodItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getFoodItemId() {
		return foodItemId;
	}
	public void setFoodItemId(int foodItemId) {
		this.foodItemId = foodItemId;
	}
	public String getFoodItemName() {
		return foodItemName;
	}
	public void setFoodItemName(String foodItemName) {
		this.foodItemName = foodItemName;
	}
	public int getFoodItemCost() {
		return foodItemCost;
	}
	public void setFoodItemCost(int foodItemCost) {
		this.foodItemCost = foodItemCost;
	}
	public int getVenueId() {
		return venueId;
	}
	public void setVenueId(int venueId) {
		this.venueId = venueId;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	@Override
	public String toString() {
		return "FoodItem [foodItemId=" + foodItemId + ", foodItemName=" + foodItemName + ", foodItemCost="
				+ foodItemCost + ", venueId=" + venueId + ", imagePath=" + imagePath  + "]";
	}
	
	
	
	

}
