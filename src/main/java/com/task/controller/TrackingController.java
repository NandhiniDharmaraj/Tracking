package com.task.controller;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.task.entity.Tracking;
import com.task.redis.RedisService;
// other imports
import com.task.responseDTO.TrackingResponseDTO;
import com.task.trackingService.TrackingService;

@RestController
@RequestMapping("/next-tracking-number")
public class TrackingController {

	private static final Logger logger = LoggerFactory.getLogger(TrackingController.class);
	private final TrackingService trackingService;
	private final RedisService redisService;

	public TrackingController(TrackingService trackingService, RedisService redisService) {
		this.trackingService = trackingService;
		this.redisService = redisService;
	}

	@GetMapping
	public ResponseEntity<?> getTrackingNumber(@RequestParam String origin_country_id,
			@RequestParam String destination_country_id, @RequestParam Double weight,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime created_at,
			@RequestParam UUID customer_id, @RequestParam String customer_name, @RequestParam String customer_slug)
			throws Exception {
		try {
			if (weight <= 0) {
				throw new IllegalArgumentException("Weight must be positive");
			}

			String redisKey = String.format("tracking:%s:%s:%s:%s", origin_country_id, destination_country_id, weight,
					customer_id);

			if (redisService.isDuplicate(redisKey, 600)) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body("Duplicate request");
			}

			Tracking record = trackingService.generateTrackingNumber(origin_country_id.toUpperCase(),
					destination_country_id.toUpperCase(), weight, created_at, customer_id.toString(), customer_name,
					customer_slug);

			return ResponseEntity.ok(new TrackingResponseDTO(record.getTrackingNumber(), record.getCreatedAt()));

		} catch (ServiceException ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Service error occurred");
		}
	}
}
