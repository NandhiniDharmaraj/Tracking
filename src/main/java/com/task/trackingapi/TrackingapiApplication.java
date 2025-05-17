package com.task.trackingapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.task.repository")
@EntityScan(basePackages = "com.task.entity")
@ComponentScan(basePackages = "com.task")
public class TrackingapiApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(TrackingapiApplication.class);
//
//		String port = "8080";
//		if (port != null) {
//			app.setDefaultProperties(Collections.singletonMap("server.port", port));
//		}

		app.run(args);
	}
}
