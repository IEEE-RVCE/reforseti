package org.ieeervce.api.siterearnouveau.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import org.assertj.core.api.Assertions;
import org.ieeervce.api.siterearnouveau.config.JWTProperties;
import org.ieeervce.api.siterearnouveau.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.ieeervce.api.siterearnouveau.jwt.JWTUtil.REFORSETI_ISSUER_CLAIM;
import static org.ieeervce.api.siterearnouveau.jwt.JWTUtil.UID_CLAIM;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JWTUtilTest {
    private static final String TEST_SIGNING_SECRET = "lsadikhesuj@OI$Q@OLI$OL!@#OL$";
    public static final int USER_ID = 1234;
    @Mock
    JWTProperties jwtProperties;
    JWTUtil jwtUtil;

    @BeforeEach
    void setUp() {
        when(jwtProperties.getSecret()).thenReturn(TEST_SIGNING_SECRET);
        jwtUtil = new JWTUtil(jwtProperties);
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

    @Test
    void testCreate() {
        User user = mock(User.class);
        when(user.getUserId()).thenReturn(USER_ID);
        String jwtString = jwtUtil.create(user);
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(TEST_SIGNING_SECRET))
                .withIssuer(REFORSETI_ISSUER_CLAIM).withClaim(UID_CLAIM, Integer.toString(USER_ID))
                .build();
        assertDoesNotThrow(() -> jwtVerifier.verify(jwtString));
    }


    private String createJWTWithUIDClaim(String passphrase, String uidClaim) {
        return JWT.create()
                .withClaim(UID_CLAIM, uidClaim)
                .sign(Algorithm.HMAC256(passphrase));
    }

}