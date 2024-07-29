package com.example.gestionevent.services;

import com.example.gestionevent.model.FoodItem;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface FoodItemService {

	public boolean addFile(MultipartFile file);

	public FoodItem addFoodItem(String foodItemName, int foodItemCost, int venueId, MultipartFile image);

	public FoodItem updateFoodItem(int foodItemId, String foodItemName, int foodItemCost, MultipartFile image);

	public List<FoodItem> getFoodItemsByVenueId(int venueId);

	public FoodItem getFoodItem(int foodItemId);

	public int deleteFoodItem(int foodItemId);

}
