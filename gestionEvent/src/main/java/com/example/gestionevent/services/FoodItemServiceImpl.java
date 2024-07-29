package com.example.gestionevent.services;

import com.example.gestionevent.model.FoodItem;
import com.example.gestionevent.repositories.FoodItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@Service
public class FoodItemServiceImpl implements FoodItemService {

	@Autowired
	FoodItemRepo foodItemRepo;

	@Value("${file.upload}")
	private String pathFile;

	// Get food Items by venueId
	@Override
	public List<FoodItem> getFoodItemsByVenueId(int venueId) {
		return foodItemRepo.findByVenueId(venueId);
	}

	// Add File
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

	@Override
	public FoodItem addFoodItem(String foodItemName, int foodItemCost, int venueId, MultipartFile image) {
		boolean fileAdded = addFile(image);
		if (!fileAdded) {
			throw new RuntimeException("Error saving the image.");
		}
		String imagePath = pathFile + image.getOriginalFilename();
		FoodItem foodItem = new FoodItem();
		foodItem.setFoodItemName(foodItemName);
		foodItem.setFoodItemCost(foodItemCost);
		foodItem.setVenueId(venueId);
		foodItem.setImagePath(imagePath);
		return foodItemRepo.save(foodItem);
	}

	// Update Food Item
	@Override
	public FoodItem updateFoodItem(int foodItemId, String foodItemName, int foodItemCost, MultipartFile image) {
		FoodItem foodItem = foodItemRepo.findById(foodItemId)
				.orElseThrow(() -> new RuntimeException("Food item not found with id: " + foodItemId));

		boolean fileAdded = true;
		String imagePath = foodItem.getImagePath();

		if (image != null && !image.isEmpty()) {
			fileAdded = addFile(image);
			imagePath = pathFile + image.getOriginalFilename();
		}

		if (!fileAdded) {
			throw new RuntimeException("Error updating the image.");
		}

		foodItem.setFoodItemName(foodItemName);
		foodItem.setFoodItemCost(foodItemCost);
		foodItem.setImagePath(imagePath);

		return foodItemRepo.save(foodItem);
	}

	// Get Food Item by Id
	@Override
	public FoodItem getFoodItem(int foodItemId) {
		return foodItemRepo.findById(foodItemId).get();
	}

	// Delete Food Item
	@Override
	public int deleteFoodItem(int foodItemId) {
		foodItemRepo.deleteById(foodItemId);
		return 1;
	}
}
