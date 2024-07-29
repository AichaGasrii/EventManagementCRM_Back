package com.example.gestionevent.rest;

import com.example.gestionevent.model.FoodItem;
import com.example.gestionevent.services.FoodItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/gestionEvent/foodItem")


public class FoodItemController {

	@Autowired
	FoodItemService foodItemService;

	// Handler for get food Items by venueId
	@GetMapping("/getFoodItems/{venueId}")
	public List<FoodItem> getFoodItemsByVenueId(@PathVariable("venueId") int venueId) {
		return foodItemService.getFoodItemsByVenueId(venueId);
	}

	// Add Food Item
	@PostMapping("/add")
	public FoodItem addFoodItem(
			@RequestParam("foodItemName") String foodItemName,
			@RequestParam("foodItemCost") int foodItemCost,
			@RequestParam("venueId") int venueId,
			@RequestParam("image") MultipartFile image) {
		return foodItemService.addFoodItem(foodItemName, foodItemCost, venueId, image);
	}

	// Update Food Item
	@PutMapping("/updateFoodItem")
	public FoodItem updateFoodItem(
			@RequestParam("foodItemId") int foodItemId,
			@RequestParam("foodItemName") String foodItemName,
			@RequestParam("foodItemCost") int foodItemCost,
			@RequestParam(value = "image", required = false) MultipartFile image) {
		return foodItemService.updateFoodItem(foodItemId, foodItemName, foodItemCost, image);
	}

	// Get Food Item by Id
	@GetMapping("/getFoodItem/{foodItemId}")
	public FoodItem getFoodItem(@PathVariable("foodItemId") int foodItemId) {
		return foodItemService.getFoodItem(foodItemId);
	}

	// Delete Food Item
	@DeleteMapping("/deleteFoodItem/{foodItemId}")
	public int deleteFoodItem(@PathVariable("foodItemId") int foodItemId) {
		return foodItemService.deleteFoodItem(foodItemId);
	}
}
