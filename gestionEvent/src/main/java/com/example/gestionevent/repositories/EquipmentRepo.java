package com.example.gestionevent.repositories;

import com.example.gestionevent.model.Equipment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipmentRepo extends CrudRepository<Equipment, Integer> {

	List<Equipment> findByVenueId(int venueId);

}
