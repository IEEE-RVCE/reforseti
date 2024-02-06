package org.ieeervce.api.siterearnouveau.jwt;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

/**
 * Miscellaneous utils to help work with JWTs
 */
@Component
public class JWTUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JWTUtil.class);

    /**
     * +- value in seconds in which we still allow JWT validity.
     * This allows for issues with system time accuracy.
     */
    private static final int JWT_LEEWAY = 10;
    private final String secret;
    private final JWTVerifier jwtVerifier;

    public JWTUtil(@Value("${jwt.secret}") String secret) {
        this.secret = secret;
        this.jwtVerifier = buildJWTVerifier();
    }

    /**
     * Verify, and unwrap to get user id in token
     * @param token encoded JWT token
     * @return User Id if valid JWT
     */
    public Optional<String> verifyAndGetUserId(String token) {
        Assert.notNull(token, "JWT Token cannot be null");
        return Optional
                .of(token)
                .flatMap(this::verifyAndDecodeJWTToken)
                .map(this::extractClaimFromDecodedJWT)
                .flatMap(this::getClaimString);
    }

    private JWTVerifier buildJWTVerifier() {
        return JWT
                .require(Algorithm.HMAC256(secret))
                .acceptLeeway(JWT_LEEWAY)
                .withClaimPresence("uid")
                .build();
    }

    private Claim extractClaimFromDecodedJWT(DecodedJWT decodedJWT) {
        return decodedJWT.getClaim("uid");
    }

    private Optional<String> getClaimString(Claim claim) {
        String claimString = claim.asString();
        if (claimString == null) {
            LOGGER.warn("Required claim is null");
            return Optional.empty();
        }
        return Optional.of(claimString);
    }

    private Optional<DecodedJWT> verifyAndDecodeJWTToken(String token) {
        try {
            return Optional.of(jwtVerifier.verify(token));
        } catch (JWTVerificationException exception) {
            LOGGER.debug("Failed to verify JWT token", exception);
            return Optional.empty();
        }
    }
}
