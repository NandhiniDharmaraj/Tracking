package com.task.responseDTO;

import java.time.OffsetDateTime;

public class TrackingResponseDTO {

	private String trackingNumber;
	private OffsetDateTime createdAt;

	public TrackingResponseDTO() {
	}

	public TrackingResponseDTO(String trackingNumber, OffsetDateTime createdAt) {
		this.trackingNumber = trackingNumber;
		this.createdAt = createdAt;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public OffsetDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(OffsetDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
