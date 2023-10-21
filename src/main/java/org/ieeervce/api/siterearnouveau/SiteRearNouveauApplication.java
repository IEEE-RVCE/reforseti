package org.ieeervce.api.siterearnouveau;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SiteRearNouveauApplication {

	@GetMapping("")
	String mainEndpoint() {
		return "IEEE-RVCE";
	}

	public static void main(String[] args) {
		SpringApplication.run(SiteRearNouveauApplication.class, args);
	}

}
