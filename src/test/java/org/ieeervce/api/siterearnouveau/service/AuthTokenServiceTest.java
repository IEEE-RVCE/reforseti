package org.ieeervce.api.siterearnouveau.service;

import org.ieeervce.api.siterearnouveau.entity.AuthToken;
import org.ieeervce.api.siterearnouveau.entity.User;
import org.ieeervce.api.siterearnouveau.jwt.JWTUtil;
import org.ieeervce.api.siterearnouveau.repository.AuthTokenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthTokenServiceTest {
    public static final String EXAMPLE_JWT = "example_jwt";
    public static final int EXAMPLE_UID = 12345678;
    public static final String EXAMPLE_UID_STRING = Integer.toString(EXAMPLE_UID);
    @Mock
    AuthTokenRepository authTokenRepository;
    @Mock
    JWTUtil jwtUtil;

    @Mock
    User user;

    @InjectMocks
    AuthTokenService authTokenService;

    @Test
    void testJWTCreation() {

        when(jwtUtil.create(user)).thenReturn(EXAMPLE_JWT);
        when(authTokenRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0, AuthToken.class));
        AuthToken token = authTokenService.create(user);
        assertThat(token).hasFieldOrPropertyWithValue("token", EXAMPLE_JWT)
                .hasFieldOrPropertyWithValue("user", user);

    }

    @Test
    void testJWTValidateAndGetUserId() {
        when(jwtUtil.verifyAndGetUserId(EXAMPLE_JWT)).thenReturn(Optional.of(EXAMPLE_UID_STRING));
        when(authTokenRepository.existsByUidAndToken(EXAMPLE_UID, EXAMPLE_JWT)).thenReturn(true);

        Optional<String> uidStringOptional = authTokenService.validateAndGetUserId(EXAMPLE_JWT);

        assertThat(uidStringOptional).isPresent().contains(EXAMPLE_UID_STRING);
    }

}