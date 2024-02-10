package org.ieeervce.api.siterearnouveau.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.assertj.core.api.Assertions;
import org.ieeervce.api.siterearnouveau.config.JWTProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

import java.util.Optional;

import static org.ieeervce.api.siterearnouveau.jwt.JWTUtil.UID_CLAIM;

class JWTUtilTest {
    private static final String TEST_SIGNING_SECRET = "abcdabcdabcdabcdabcdabcd";
    @Spy
    JWTProperties jwtProperties;
    @InjectMocks
    JWTUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtProperties.setSecret(TEST_SIGNING_SECRET);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "a", "12345678"})
    void verifyAndGetUserIdWorks(String uidClaim) {
        String jwt = createJWTWithUIDClaim(TEST_SIGNING_SECRET, uidClaim);
        Optional<String> jwtClaimOptional = jwtUtil.verifyAndGetUserId(jwt);
        Assertions.assertThat(jwtClaimOptional).contains(uidClaim);
    }

    @ParameterizedTest
    @ValueSource(strings = {"bcdbcdbcdbcdbcdbcdbcdbcdbcdbcd", "th1SisAverYlongpassphrase"})
    void verifyAndGetUserIdReturnsEmptyWithPassphrase(String passphrase) {
        String uidClaim = "1";
        String jwt = createJWTWithUIDClaim(passphrase, uidClaim);

        Optional<String> jwtClaimOptional = jwtUtil.verifyAndGetUserId(jwt);
        Assertions.assertThat(jwtClaimOptional).isEmpty();
    }

    @Test
    void verifyAndGetUserIdReturnsEmptyIfInvalidClaim() {
        String jwt = JWT.create().withClaim(UID_CLAIM, 1)
                .sign(Algorithm.HMAC256(JWTUtilTest.TEST_SIGNING_SECRET));

        Optional<String> jwtClaimOptional = jwtUtil.verifyAndGetUserId(jwt);
        Assertions.assertThat(jwtClaimOptional).isEmpty();
    }

    @Test
    void verifyAndGetUserIdReturnsEmptyIfEmptyClaim() {
        String jwt = JWT.create()
                .sign(Algorithm.HMAC256(JWTUtilTest.TEST_SIGNING_SECRET));

        Optional<String> jwtClaimOptional = jwtUtil.verifyAndGetUserId(jwt);
        Assertions.assertThat(jwtClaimOptional).isEmpty();
    }


    private String createJWTWithUIDClaim(String passphrase, String uidClaim) {
        return JWT.create()
                .withClaim(UID_CLAIM, uidClaim)
                .sign(Algorithm.HMAC256(passphrase));
    }

}