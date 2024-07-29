package com.example.gestionevent.services;

import com.example.gestionevent.model.Equipment;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EquipmentService {

	public boolean addFile(MultipartFile file);

	public Equipment addEquipment(String equipmentName, int equipmentCost, MultipartFile image, int venueId);

	public Equipment updateEquipment(int equipmentId, String equipmentName, int equipmentCost, MultipartFile image);

	public List<Equipment> getEquipmentsByVenueId(int venueId);

	public Equipment getEquipment(int equipmentId);

	public int deleteEquipment(int equipmentId);
}
