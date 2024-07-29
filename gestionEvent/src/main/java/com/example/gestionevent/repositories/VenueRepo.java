package com.example.gestionevent.repositories;

import com.example.gestionevent.model.Venue;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VenueRepo extends CrudRepository<Venue, Integer> {

	List<Venue> findByUserName(String userName);

	List<Venue> findByVenuePlace(String place);

}
