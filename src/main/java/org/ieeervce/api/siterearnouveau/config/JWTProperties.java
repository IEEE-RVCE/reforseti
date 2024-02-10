package org.ieeervce.api.siterearnouveau.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * JWT Configuration secrets encapsulated in as configuration
 */
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JWTProperties {
    private String secret;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

}
