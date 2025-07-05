package com.sparrows.board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BackendMonoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendMonoApplication.class, args);
	}
}
