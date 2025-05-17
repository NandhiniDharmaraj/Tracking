package com.task.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.task.entity.Tracking;

@Repository
public interface TrackingRepository extends JpaRepository<Tracking, String> {

	Optional<Tracking> findByTrackingNumber(String trackingNumber);
}
