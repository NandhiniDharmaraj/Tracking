package com.task.trackingService;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.OffsetDateTime;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.task.entity.Tracking;
import com.task.repository.TrackingRepository;

@Service
public class TrackingService {

	private final TrackingRepository repository;
	private final Random random = new Random();

	public TrackingService(TrackingRepository repository) {
		this.repository = repository;
	}

	@Transactional
	public synchronized Tracking generateTrackingNumber(String originCountryId, String destinationCountryId,
			Double weight, OffsetDateTime createdAt, String customerId, String customerName, String customerSlug)
			throws Exception {
		int maxLength = 16;
		String baseString = originCountryId + destinationCountryId + weight.toString() + createdAt.toString()
				+ customerId + customerSlug + System.nanoTime();
		String trackingNumber;
		int attempts = 0;
		do {
			String saltedString = baseString + (attempts == 0 ? "" : random.nextInt(9999));
			trackingNumber = generateTrackingNumberFromString(saltedString, maxLength);
			Optional<Tracking> existing = repository.findByTrackingNumber(trackingNumber);
			if (existing.isEmpty()) {
				break;
			}
			attempts++;
			if (attempts > 5) {
				throw new RuntimeException("Failed to generate unique tracking number after retries");
			}
		} while (true);

		Tracking record = new Tracking(trackingNumber, OffsetDateTime.now(), originCountryId, destinationCountryId,
				weight, customerId);

		return repository.save(record);
	}

	private String generateTrackingNumberFromString(String input, int maxLength) throws Exception {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
		BigInteger number = new BigInteger(1, hash);
		String base36 = number.toString(36).toUpperCase(Locale.ROOT);
		if (base36.length() > maxLength) {
			return base36.substring(0, maxLength);
		} else {
			return base36;
		}
	}
}
