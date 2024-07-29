package com.example.gestionevent.repositories;

import com.example.gestionevent.model.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepo extends CrudRepository<Event, Integer> {

	List<Event> findByVenueId(int venueId);

	Event findByEventNameAndVenueId(String eName, int vId);

}
