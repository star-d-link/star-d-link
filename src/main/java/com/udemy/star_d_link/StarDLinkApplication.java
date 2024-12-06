package com.udemy.star_d_link;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableJpaAuditing
@EnableWebSecurity(debug = true)
public class StarDLinkApplication {

	public static void main(String[] args) {
		SpringApplication.run(StarDLinkApplication.class, args);
	}

}
