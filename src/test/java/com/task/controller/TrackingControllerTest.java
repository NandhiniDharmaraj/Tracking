package com.task.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.task.entity.Tracking;
import com.task.redis.RedisService;
import com.task.trackingService.TrackingService;
import com.task.trackingapi.TrackingapiApplication;

@SpringBootTest(classes = TrackingapiApplication.class)
@AutoConfigureMockMvc
public class TrackingControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TrackingService trackingService;

	@MockBean
	private RedisService redisService;

	@BeforeEach
	void setup() {
		when(redisService.isDuplicate(anyString(), anyLong())).thenReturn(false);

		Tracking mockTracking = new Tracking();
		mockTracking.setTrackingNumber("TRACK123");
		mockTracking.setCreatedAt(OffsetDateTime.now());

		when(trackingService.generateTrackingNumber(anyString(), anyString(), anyDouble(), any(), anyString(),
				anyString(), anyString())).thenReturn(mockTracking);
	}

	@Test
	void testGetTrackingNumber_success() throws Exception {
		mockMvc.perform(get("/next-tracking-number").param("origin_country_id", "US")
				.param("destination_country_id", "IN").param("weight", "1.2")
				.param("created_at", OffsetDateTime.now().toString()).param("customer_id", UUID.randomUUID().toString())
				.param("customer_name", "Test User").param("customer_slug", "test-user")).andExpect(status().isOk());
	}
}
