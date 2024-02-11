package org.ieeervce.api.siterearnouveau.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import org.assertj.core.api.Assertions;
import org.ieeervce.api.siterearnouveau.auth.AuthUserDetails;
import org.ieeervce.api.siterearnouveau.service.AuthTokenService;
import org.ieeervce.api.siterearnouveau.service.AuthUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.ieeervce.api.siterearnouveau.jwt.JWTAuthenticationFilter.AUTHORIZATION_HEADER_NAME;
import static org.ieeervce.api.siterearnouveau.jwt.JWTAuthenticationFilter.AUTH_JWT_REQUEST_ATTRIBUTE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JWTAuthenticationFilterTest {

    private static final String EXAMPLE_JWT = "EXAMPLE_JWT";
    private static final String AUTH_HEADER_VALUE = "Bearer " + EXAMPLE_JWT;
    private static final String INCORRECT_AUTH_HEADER_VALUE = "ABCD " + EXAMPLE_JWT;
    public static final String EXAMPLE_USER_ID = "1234";
    @Mock
    AuthTokenService authTokenService;
    @Mock
    AuthUserDetailsService authUserDetailsService;
    @Mock
    SecurityContext securityContext;
    @Mock
    MockHttpServletRequest mockHttpServletRequest;
    @Mock
    MockHttpServletResponse mockHttpServletResponse;
    @Mock
    private FilterChain filterChain;
    @Mock
    AuthUserDetails authUserDetails;
    @Captor
    ArgumentCaptor<Authentication> authenticationCaptor;
    @InjectMocks
    JWTAuthenticationFilter jwtAuthenticationFilter;

    @BeforeEach
    void setUp() {


    }

    @Test
    void doFilterInternalWithCorrectJWT() throws ServletException, IOException {
        try (MockedStatic<SecurityContextHolder> mockedSecurityContextHolder = Mockito.mockStatic(SecurityContextHolder.class)) {

            when(mockHttpServletRequest.getHeader(AUTHORIZATION_HEADER_NAME)).thenReturn(AUTH_HEADER_VALUE);
            when(authTokenService.validateAndGetUserId(EXAMPLE_JWT)).thenReturn(Optional.of(EXAMPLE_USER_ID));
            when(authUserDetailsService.loadUserByUsername(EXAMPLE_USER_ID)).thenReturn(authUserDetails);

            mockedSecurityContextHolder.when(() -> SecurityContextHolder.getContext()).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(null);

            jwtAuthenticationFilter.doFilterInternal(mockHttpServletRequest, mockHttpServletResponse, filterChain);

            verify(mockHttpServletRequest).setAttribute(AUTH_JWT_REQUEST_ATTRIBUTE, EXAMPLE_JWT);
            verify(securityContext).setAuthentication(authenticationCaptor.capture());
            verify(filterChain).doFilter(mockHttpServletRequest,mockHttpServletResponse);

            Authentication authentication = authenticationCaptor.getValue();
            assertThat(authentication)
                    .isInstanceOf(UsernamePasswordAuthenticationToken.class)
                    .extracting(Authentication::getPrincipal).isSameAs(authUserDetails);
        }
    }
    @Test
    void doFilterInternalWithAuthAlreadyPresentNoAuthProcessing() throws ServletException, IOException {
        Authentication alreadyPresentAuthentication = mock(Authentication.class);
        try (MockedStatic<SecurityContextHolder> mockedSecurityContextHolder = Mockito.mockStatic(SecurityContextHolder.class)) {

            when(mockHttpServletRequest.getHeader(AUTHORIZATION_HEADER_NAME)).thenReturn(AUTH_HEADER_VALUE);
            when(authTokenService.validateAndGetUserId(EXAMPLE_JWT)).thenReturn(Optional.of(EXAMPLE_USER_ID));
            when(authUserDetailsService.loadUserByUsername(EXAMPLE_USER_ID)).thenReturn(authUserDetails);

            mockedSecurityContextHolder.when(() -> SecurityContextHolder.getContext()).thenReturn(securityContext);
            when(securityContext.getAuthentication()).thenReturn(alreadyPresentAuthentication);

            jwtAuthenticationFilter.doFilterInternal(mockHttpServletRequest, mockHttpServletResponse, filterChain);

            verify(mockHttpServletRequest,never()).setAttribute(AUTH_JWT_REQUEST_ATTRIBUTE, EXAMPLE_JWT);
            verify(securityContext,never()).setAuthentication(any());
            verify(filterChain).doFilter(mockHttpServletRequest,mockHttpServletResponse);
            
        }
    }
}