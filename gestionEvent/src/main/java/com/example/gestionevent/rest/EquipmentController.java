package com.example.gestionevent.rest;

import com.example.gestionevent.model.Equipment;
import com.example.gestionevent.services.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/gestionEvent/equipment")


public class EquipmentController {

	@Autowired
	EquipmentService equipmentService;

	@GetMapping("/getEquipments/{venueId}")
	public List<Equipment> getEquipmentsByVenueId(@PathVariable("venueId") int venueId) {
		return equipmentService.getEquipmentsByVenueId(venueId);
	}

	@PostMapping("/add")
	public Equipment addEquipment(
			@RequestParam("equipmentName") String equipmentName,
			@RequestParam("equipmentCost") int equipmentCost,
			@RequestParam("image") MultipartFile image,
			@RequestParam("venueId") int venueId) {
		return equipmentService.addEquipment(equipmentName, equipmentCost, image, venueId);
	}

	@GetMapping("/getEquipment/{equipmentId}")
	public Equipment getEquipment(@PathVariable("equipmentId") int equipmentId) {
		return equipmentService.getEquipment(equipmentId);
	}

	@PutMapping("/updateEquipment")
	public Equipment updateEquipment(
			@RequestParam("equipmentId") int equipmentId,
			@RequestParam("equipmentName") String equipmentName,
			@RequestParam("equipmentCost") int equipmentCost,
			@RequestParam(value = "image", required = false) MultipartFile image) {
		return equipmentService.updateEquipment(equipmentId, equipmentName, equipmentCost, image);
	}

	@DeleteMapping("/deleteEquipment/{equipmentId}")
	public int deleteEquipment(@PathVariable("equipmentId") int equipmentId) {
		return equipmentService.deleteEquipment(equipmentId);
	}
}
