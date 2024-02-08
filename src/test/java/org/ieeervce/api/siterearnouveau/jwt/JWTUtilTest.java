package org.ieeervce.api.siterearnouveau.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Spy;

import java.util.Optional;

class JWTUtilTest {
    private static final String TEST_SIGNING_SECRET = "abcdabcdabcdabcdabcdabcd";

    @Spy
    JWTUtil jwtUtil = new JWTUtil(TEST_SIGNING_SECRET);

    @BeforeEach
    void setUp() {
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
        String jwt = JWT.create().withClaim("uid", 1)
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
                .withClaim("uid", uidClaim)
                .sign(Algorithm.HMAC256(passphrase));
    }

}