package org.ieeervce.api.siterearnouveau.jwt;

import org.ieeervce.api.siterearnouveau.service.AuthTokenService;
import org.ieeervce.api.siterearnouveau.service.AuthUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JWTAuthenticationFilterTest {

    @Mock
    AuthTokenService authTokenService;
    @Mock
    AuthUserDetailsService authUserDetailsService;

    @InjectMocks
    JWTAuthenticationFilter jwtAuthenticationFilter;
    @BeforeEach
    void setUp() {
    }

    @Test
    void doFilterInternal() {
    }
}