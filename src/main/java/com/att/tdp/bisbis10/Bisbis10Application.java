package com.att.tdp.bisbis10;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Main application class for Bisbis10.
 */
@SpringBootApplication
@EnableJpaRepositories("com.att.tdp.bisbis10.*")
@ComponentScan(basePackages = { "com.att.tdp.bisbis10.*" })
@EntityScan("com.att.tdp.bisbis10.*")
public class Bisbis10Application {

	/**
	 * Main method to start the Bisbis10 application.
	 *
	 * @param args Command line arguments (unused).
	 */
	public static void main(final String[] args) {
		SpringApplication.run(Bisbis10Application.class, args);
	}

}
