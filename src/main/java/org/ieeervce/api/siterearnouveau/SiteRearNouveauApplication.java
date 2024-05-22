package org.ieeervce.api.siterearnouveau;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SiteRearNouveauApplication {
	static final String IEEE_RVCE = "IEEE-RVCE";

	@GetMapping
	String mainEndpoint() {
		return IEEE_RVCE;
	}

	public static void main(String[] args) {
		SpringApplication.run(SiteRearNouveauApplication.class, args);
	}

}
