package com.arjun.appointment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.IOException;

@EnableJpaRepositories
@SpringBootApplication
public class AppointmentBookingApplication {
	public static void main(String[] args) throws IOException {
		SpringApplication.run(AppointmentBookingApplication.class, args);
	}
}
