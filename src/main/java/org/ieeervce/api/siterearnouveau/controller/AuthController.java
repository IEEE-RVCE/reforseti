package org.ieeervce.api.siterearnouveau.controller;

import io.micrometer.core.annotation.Timed;
import jakarta.servlet.http.HttpServletRequest;
import org.ieeervce.api.siterearnouveau.auth.AuthUserDetails;
import org.ieeervce.api.siterearnouveau.dto.ResultsDTO;
import org.ieeervce.api.siterearnouveau.dto.auth.UsernamePasswordDTO;
import org.ieeervce.api.siterearnouveau.entity.AuthToken;
import org.ieeervce.api.siterearnouveau.entity.User;
import org.ieeervce.api.siterearnouveau.exception.LoginFailedException;
import org.ieeervce.api.siterearnouveau.jwt.JWTAuthenticationFilter;
import org.ieeervce.api.siterearnouveau.service.AuthTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

/**
 * Authentication controller
 */
@RestController
@RequestMapping("/api/auth")
@Timed
public class AuthController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
    static final String FAILED_TO_LOG_IN = "Failed to log in";
    private final AuthenticationManager authenticationManager;

    private final AuthTokenService authTokenService;

    public AuthController(AuthenticationManager authenticationManager, AuthTokenService authTokenService) {
        this.authenticationManager = authenticationManager;
        this.authTokenService = authTokenService;
    }

    /**
     * Login request. Returns a JWT token that contains the userid
     *
     * @param usernamePasswordDTO Userid and password
     * @return Token if valid
     */
    @PostMapping
    ResultsDTO<String> login(@RequestBody UsernamePasswordDTO usernamePasswordDTO) throws LoginFailedException {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usernamePasswordDTO.getUserId(), usernamePasswordDTO.getPassword()));
            if (authentication.isAuthenticated()) {
                AuthUserDetails authUserDetails = (AuthUserDetails) authentication.getPrincipal();
                User user = authUserDetails.getUser();
                AuthToken authToken = authTokenService.create(user);
                return new ResultsDTO<>(authToken.getToken());
            }
        } catch (AuthenticationException exception) {
            LOGGER.debug("Login failed for user={}",usernamePasswordDTO.getUserId(),exception);
        }
        LOGGER.info("Login failed for user={}",usernamePasswordDTO.getUserId());
        throw new LoginFailedException(FAILED_TO_LOG_IN);
    }

    @DeleteMapping
    ResultsDTO<Void> logout(HttpServletRequest httpServletRequest) {
        String jwtToken = (String) httpServletRequest.getAttribute(JWTAuthenticationFilter.AUTH_JWT_REQUEST_ATTRIBUTE);
        authTokenService.remove(jwtToken);
        return new ResultsDTO<>(null);
    }
}


