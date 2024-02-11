package org.ieeervce.api.siterearnouveau.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.ieeervce.api.siterearnouveau.auth.AuthUserDetails;
import org.ieeervce.api.siterearnouveau.service.AuthTokenService;
import org.ieeervce.api.siterearnouveau.service.AuthUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

/**
 * Once per filter to add jwt authentication details
 */
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(JWTAuthenticationFilter.class);
    private static final String BEARER_HEADER_START = "Bearer ";
    private static final int HEADER_TOKEN_BEGIN_INDEX = 7;
    public static final String AUTH_JWT_REQUEST_ATTRIBUTE = "AUTH_JWT";

    private final AuthTokenService authTokenService;
    private final AuthUserDetailsService authUserDetailsService;

    public JWTAuthenticationFilter(AuthTokenService authTokenService, AuthUserDetailsService authUserDetailsService) {
        this.authTokenService = authTokenService;
        this.authUserDetailsService = authUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        LOGGER.debug("Verifying JWT header");
        Optional<String> authHeaderValue = Optional
                .ofNullable(request.getHeader("Authorization"))
                .flatMap(this::mapHeaderValue);


        Optional<String> userIdOptional = authHeaderValue.flatMap(authTokenService::validateAndGetUserId);
        userIdOptional.ifPresent(userId->{
            AuthUserDetails userDetails = authUserDetailsService.loadUserByUsername(userId);
            SecurityContext securityContext =  SecurityContextHolder.getContext();

            if(securityContext.getAuthentication()==null){
                LOGGER.debug("Logging in user_id={}",userIdOptional);
                request.setAttribute(AUTH_JWT_REQUEST_ATTRIBUTE,authHeaderValue.get());
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                securityContext.setAuthentication(usernamePasswordAuthenticationToken);
            }
        });
        filterChain.doFilter(request, response);
    }

    private Optional<String> mapHeaderValue(String authHeaderValue) {
        return Optional.of(authHeaderValue).filter(e -> e.startsWith(BEARER_HEADER_START)).map(e -> e.substring(HEADER_TOKEN_BEGIN_INDEX));
    }

}