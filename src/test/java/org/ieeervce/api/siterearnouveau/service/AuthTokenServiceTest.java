package org.ieeervce.api.siterearnouveau.service;

import org.ieeervce.api.siterearnouveau.jwt.JWTUtil;
import org.ieeervce.api.siterearnouveau.repository.AuthTokenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthTokenServiceTest {
    @Mock
    AuthTokenRepository authTokenRepository;
    @Mock
    JWTUtil jwtUtil;

    @InjectMocks
    AuthTokenService authTokenService;

    @Test
    void testJWTCreation(){

    }

}