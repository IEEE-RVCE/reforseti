package org.ieeervce.api.siterearnouveau.config;

import org.ieeervce.api.siterearnouveau.auth.jwt.JWTAuthenticationFilter;
import org.ieeervce.api.siterearnouveau.auth.jwt.JWTUtil;
import org.ieeervce.api.siterearnouveau.service.AuthUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    /**
     * Password encoder to use for decoding and encoding passwords.
     * <p>
     * Using Bcrypt as it is the existing password encoder.
     * 
     * @return Bcrypt password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter(JWTUtil jwtUtil, AuthUserDetailsService authUserDetailsService){
        return new JWTAuthenticationFilter(jwtUtil,authUserDetailsService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(JWTAuthenticationFilter jwtAuthenticationFilter,HttpSecurity httpSecurity) throws Exception {
        // FIXME re-enable cors
        httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(SecurityConfig::getCustomizedHttpAuthorization)
                .sessionManagement(SecurityConfig::customizeSessionManagement)
                .addFilterBefore(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class)
                .formLogin(Customizer.withDefaults())
                .httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }

    private static void getCustomizedHttpAuthorization(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry customizer) {
        customizer
                .requestMatchers(HttpMethod.POST, "/api/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/api/**").authenticated()
                .anyRequest().permitAll();
    }


    private static void customizeSessionManagement(SessionManagementConfigurer<HttpSecurity> sessionManagement) {
        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

}
