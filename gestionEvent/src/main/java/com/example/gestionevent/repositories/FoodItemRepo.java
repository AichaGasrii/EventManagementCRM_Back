package com.example.gestionevent.repositories;

import com.example.gestionevent.model.FoodItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodItemRepo extends CrudRepository<FoodItem, Integer> {

	List<FoodItem> findByVenueId(int venueId);

}
