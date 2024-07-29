package com.example.gestionevent.services;

import com.example.gestionevent.model.Equipment;
import com.example.gestionevent.repositories.EquipmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

@org.springframework.stereotype.Service
public class EquipmentServiceImpl implements EquipmentService {

	@Autowired
	EquipmentRepo equipmentRepo;

	@Value("${file.upload}")
	private String pathFile;

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
	public Equipment addEquipment(String equipmentName, int equipmentCost, MultipartFile image, int venueId) {
		boolean fileAdded = addFile(image);
		if (!fileAdded) {
			throw new RuntimeException("Error saving the image.");
		}
		String imagePath = pathFile + image.getOriginalFilename();
		Equipment equipment = new Equipment();
		equipment.setEquipmentName(equipmentName);
		equipment.setEquipmentCost(equipmentCost);
		equipment.setImagePath(imagePath);
		equipment.setVenueId(venueId);
		return equipmentRepo.save(equipment);
	}

	@Override
	public Equipment updateEquipment(int equipmentId, String equipmentName, int equipmentCost, MultipartFile image) {
		Equipment equipment = equipmentRepo.findById(equipmentId)
				.orElseThrow(() -> new RuntimeException("Equipment not found with id: " + equipmentId));

		boolean fileAdded = true;
		String imagePath = equipment.getImagePath();

		if (image != null && !image.isEmpty()) {
			fileAdded = addFile(image);
			imagePath = pathFile + image.getOriginalFilename();
		}

		if (!fileAdded) {
			throw new RuntimeException("Error updating the image.");
		}

		equipment.setEquipmentName(equipmentName);
		equipment.setEquipmentCost(equipmentCost);
		equipment.setImagePath(imagePath);

		return equipmentRepo.save(equipment);
	}

	@Override
	public List<Equipment> getEquipmentsByVenueId(int venueId) {
		return equipmentRepo.findByVenueId(venueId);
	}

	@Override
	public Equipment getEquipment(int equipmentId) {
		return equipmentRepo.findById(equipmentId).get();
	}



	@Override
	public int deleteEquipment(int equipmentId) {
		equipmentRepo.deleteById(equipmentId);
		return 1;
	}
}
