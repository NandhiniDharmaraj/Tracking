package com.task.entity;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "tracking_numbers", uniqueConstraints = @UniqueConstraint(columnNames = "trackingNumber"))
public class Tracking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	@Column(length = 16, nullable = false, unique = true)
	private String trackingNumber;

	@Column(nullable = false)
	private OffsetDateTime createdAt;

	@Column(length = 2)
	private String originCountryId;

	@Column(length = 2)
	private String destinationCountryId;

	@Column(precision = 10, scale = 3)
	private Double weight;

	@Column(nullable = false)
	private String customerId;

	public Tracking() {
	}

	public Tracking(String trackingNumber, OffsetDateTime createdAt, String originCountryId,
			String destinationCountryId, Double weight, String customerId) {
		this.trackingNumber = trackingNumber;
		this.createdAt = createdAt;
		this.originCountryId = originCountryId;
		this.destinationCountryId = destinationCountryId;
		this.weight = weight;
		this.customerId = customerId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getOriginCountryId() {
		return originCountryId;
	}

	public void setOriginCountryId(String originCountryId) {
		this.originCountryId = originCountryId;
	}

	public String getDestinationCountryId() {
		return destinationCountryId;
	}

	public void setDestinationCountryId(String destinationCountryId) {
		this.destinationCountryId = destinationCountryId;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

}
