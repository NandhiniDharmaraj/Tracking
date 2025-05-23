package com.task.trackingService;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.OffsetDateTime;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.task.entity.Tracking;
import com.task.exception.TrackingException;
import com.task.repository.TrackingRepository;

@Service
public class TrackingService {

	private static final Logger logger = LoggerFactory.getLogger(TrackingService.class);
	private static final int MAX_LENGTH = 16;
	private static final int MAX_ATTEMPTS = 5;

	private final TrackingRepository repository;
	private final Random random = new Random();

	public TrackingService(TrackingRepository repository) {
		this.repository = repository;
	}

	@Transactional
	public synchronized Tracking generateTrackingNumber(String originCountryId, String destinationCountryId,
			Double weight, OffsetDateTime createdAt, String customerId, String customerName, String customerSlug) {

		String baseString = originCountryId + destinationCountryId + weight + createdAt + customerId + customerSlug
				+ System.nanoTime();
		String trackingNumber;
		int attempts = 0;

		logger.info("Generating tracking number for customerId, origin, destination", customerId, originCountryId,
				destinationCountryId);

		do {
			String saltedString = baseString + (attempts == 0 ? "" : random.nextInt(9999));
			try {
				trackingNumber = generateTrackingNumberFromString(saltedString, MAX_LENGTH);
			} catch (Exception e) {
				logger.error("Hashing failed while generating tracking number", e);
				throw new TrackingException("Hashing failed", e);
			}

			Optional<Tracking> existing = repository.findByTrackingNumber(trackingNumber);
			if (existing.isEmpty()) {
				break;
			}

			attempts++;
			logger.warn("Tracking number collision detected (attempt ). Retrying...", attempts);

			if (attempts > MAX_ATTEMPTS) {
				logger.error("Exceeded max attempts to generate a unique tracking number");
				throw new TrackingException(
						"Failed to generate unique tracking number after " + MAX_ATTEMPTS + " attempts");
			}
		} while (true);

		Tracking record = new Tracking(trackingNumber, OffsetDateTime.now(), originCountryId, destinationCountryId,
				weight, customerId);
		Tracking saved = repository.save(record);

		logger.info("Successfully generated tracking number:", saved.getTrackingNumber());

		return saved;
	}

	private String generateTrackingNumberFromString(String input, int maxLength) throws Exception {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
		BigInteger number = new BigInteger(1, hash);
		String base36 = number.toString(36).toUpperCase(Locale.ROOT);
		return base36.length() > maxLength ? base36.substring(0, maxLength) : base36;
	}
}
