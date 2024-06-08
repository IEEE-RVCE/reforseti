package org.ieeervce.api.siterearnouveau.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.ieeervce.api.siterearnouveau.auth.AuthUserDetails;
import org.ieeervce.api.siterearnouveau.controller.error.ExceptionControllerAdvice;
import org.ieeervce.api.siterearnouveau.dto.auth.UserRegistrationDTO;
import org.ieeervce.api.siterearnouveau.dto.auth.UsernamePasswordDTO;
import org.ieeervce.api.siterearnouveau.entity.AuthToken;
import org.ieeervce.api.siterearnouveau.entity.User;
import org.ieeervce.api.siterearnouveau.jwt.JWTAuthenticationFilter;
import org.ieeervce.api.siterearnouveau.service.AuthTokenService;
import org.ieeervce.api.siterearnouveau.service.AuthUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class AuthControllerTest {
    public static final String EXAMPLE_JWT = "example_jwt";
    public static final String EXAMPLE_USER_ID = "1234";
    public static final String EXAMPLE_USER_PASSWORD = "pass";
    public static final int USER_ID = 12345678;
    ObjectMapper objectMapper = new ObjectMapper();
    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    AuthTokenService authTokenService;
    @Mock
    Authentication authentication;
    @Mock
    AuthUserDetails authUserDetails;
    @Mock
    User user;
    @Mock
    AuthToken authToken;
    @Spy
    ModelMapper modelMapper;
    @Spy
    PasswordEncoder passwordEncoder;
    @Mock
    AuthUserDetailsService authUserDetailsService;
    UsernamePasswordDTO usernamePasswordDTO = new UsernamePasswordDTO();

    @InjectMocks
    AuthController authController;

    @InjectMocks
    ExceptionControllerAdvice exceptionControllerAdvice;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        usernamePasswordDTO.setUserId(EXAMPLE_USER_ID);
        usernamePasswordDTO.setPassword(EXAMPLE_USER_PASSWORD);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).setControllerAdvice(exceptionControllerAdvice).build();
    }

    @Test
    void loginWithSuccessfulCredentials() throws Exception {
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(authUserDetails);
        when(authUserDetails.getUser()).thenReturn(user);
        when(authTokenService.create(user)).thenReturn(authToken);
        when(authToken.getToken()).thenReturn(EXAMPLE_JWT);

        mockMvc
                .perform(post("/api/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonBytes(usernamePasswordDTO))
                        .requestAttr(JWTAuthenticationFilter.AUTH_JWT_REQUEST_ATTRIBUTE, EXAMPLE_JWT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ok", equalTo(true)))
                .andExpect(jsonPath("$.response", equalTo(EXAMPLE_JWT)));
    }

    @Test
    void loginWithoutGoodCredentials() throws Exception {
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(false);
        mockMvc
                .perform(post("/api/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonBytes(usernamePasswordDTO))
                        .requestAttr(JWTAuthenticationFilter.AUTH_JWT_REQUEST_ATTRIBUTE, EXAMPLE_JWT))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.ok", equalTo(false)))
                .andExpect(jsonPath("$.message", equalTo("Failed to log in")))
                .andExpect(jsonPath("$.response", nullValue()));
    }

    @Test
    void loginWithoutGoodCredentialsAndAuthFailure() throws Exception {
        when(authenticationManager.authenticate(any())).thenThrow(new AuthenticationCredentialsNotFoundException("User does not exist"));
        mockMvc
                .perform(post("/api/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJsonBytes(usernamePasswordDTO))
                        .requestAttr(JWTAuthenticationFilter.AUTH_JWT_REQUEST_ATTRIBUTE, EXAMPLE_JWT))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.ok", equalTo(false)))
                .andExpect(jsonPath("$.message", equalTo("Failed to log in")))
                .andExpect(jsonPath("$.response", nullValue()));
    }

    @Test
    void logout() throws Exception {
        mockMvc
                .perform(delete("/api/auth").requestAttr(JWTAuthenticationFilter.AUTH_JWT_REQUEST_ATTRIBUTE, EXAMPLE_JWT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ok", equalTo(true)));
        verify(authTokenService).remove(EXAMPLE_JWT);

    }

    @Test
    void testRegister() throws Exception {
        UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
        userRegistrationDTO.setUserId(USER_ID);
        userRegistrationDTO.setRole("BIG ROLE");
        userRegistrationDTO.setPassword("myAwesomePassword");
        userRegistrationDTO.setEmail("abcd@gmail.com");
        userRegistrationDTO.setFirstName("First");
        userRegistrationDTO.setLastName("Last");
        userRegistrationDTO.setSocietyId(2);
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        when(authUserDetailsService.createIfNotExists(userArgumentCaptor.capture())).thenReturn(null);
        when(passwordEncoder.encode(anyString())).thenReturn("averyencodedpassword");

        mockMvc.perform(post("/api/auth/register").contentType(MediaType.APPLICATION_JSON).content(toJsonBytes(userRegistrationDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ok", Matchers.is(true)))
                .andExpect(jsonPath("$.response.userId", equalTo(USER_ID)));

        User savedUser = userArgumentCaptor.getValue();
        Assertions.assertThat(savedUser).isNotNull().extracting(User::getPassword).isEqualTo("averyencodedpassword");


    }

    private <T> byte[] toJsonBytes(T object) throws JsonProcessingException {
        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsBytes(object);
    }

}