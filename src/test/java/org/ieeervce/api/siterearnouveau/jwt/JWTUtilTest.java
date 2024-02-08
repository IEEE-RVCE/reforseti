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
        String jwt = createJWT(TEST_SIGNING_SECRET, uidClaim);
        Optional<String> jwtClaimOptional = jwtUtil.verifyAndGetUserId(jwt);
        Assertions.assertThat(jwtClaimOptional).contains(uidClaim);
    }

    @ParameterizedTest
    @ValueSource(strings = {"bcdbcdbcdbcdbcdbcdbcdbcdbcdbcd", "th1SisAverYlongpassphrase"})
    void verifyAndGetUserIdReturnsEmptyWithPassphrase(String passphrase) {
        String uidClaim = "1";
        String jwt = createJWT(passphrase, uidClaim);

        Optional<String> jwtClaimOptional = jwtUtil.verifyAndGetUserId(jwt);
        Assertions.assertThat(jwtClaimOptional).isEmpty();
    }


    private String createJWT(String passphrase, String uidClaim) {
        return JWT.create()
                .withClaim("uid", uidClaim)
                .sign(Algorithm.HMAC256(passphrase));
    }

}