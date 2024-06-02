package org.ieeervce.api.siterearnouveau.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;


/**
 * Conditional Cors.
 * When using the local profile, we use a configurable value.
 * When this profile is not activated, the production endpoints are used.
 */
@Configuration
public class CorsConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(CorsConfig.class);
    private static final List<String> PRODUCTION_ALLOWED_ORIGINS = Arrays.asList(
            "https://ieee-rvce.org",
            "https://www.ieee-rvce.org",
            "https://csitss.ieee-rvce.org"
    );

    @Bean("reforsetiCorsConfig")
    @Profile("local")
    @ConditionalOnProperty("cors.local.origin")
    CorsConfigurationSource corsConfigurationSource(@Value("${cors.local.origin}") String origin) {
        LOGGER.info("Using local cors config, with origin={}",origin);
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin(origin);

        configuration.setAllowCredentials(true);
        configuration.addAllowedMethod("GET");
        configuration.addAllowedMethod("POST");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean("reforsetiCorsConfig")
    @Profile("!local")
    CorsConfigurationSource regularCorsConfiguration() {
        LOGGER.info("Started non-local cors config with origin={}",PRODUCTION_ALLOWED_ORIGINS);
        CorsConfiguration configuration = new CorsConfiguration();

        PRODUCTION_ALLOWED_ORIGINS.forEach(configuration::addAllowedOrigin);

        configuration.setAllowCredentials(true);
        configuration.addAllowedMethod("GET");
        configuration.addAllowedMethod("POST");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        LOGGER.info("Ended cors config");

        return source;
    }

}
