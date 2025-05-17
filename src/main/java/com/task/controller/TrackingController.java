package com.task.controller;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.task.entity.Tracking;
import com.task.responseDTO.TrackingResponseDTO;
import com.task.trackingService.TrackingService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/next-tracking-number")
public class TrackingController {

	private final TrackingService trackingService;

	public TrackingController(TrackingService trackingService) {
		this.trackingService = trackingService;
	}

	@GetMapping
	public ResponseEntity<?> getTrackingNumber(@RequestParam String origin_country_id,
			@RequestParam String destination_country_id, @RequestParam Double weight,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime created_at,
			@RequestParam UUID customer_id, @RequestParam String customer_name, @RequestParam String customer_slug)
			throws Exception {

		Tracking record = trackingService.generateTrackingNumber(origin_country_id.toUpperCase(),
				destination_country_id.toUpperCase(), weight, created_at, customer_id.toString(), customer_name,
				customer_slug);

		return ResponseEntity.ok(new TrackingResponseDTO(record.getTrackingNumber(), record.getCreatedAt()));
	}

}
