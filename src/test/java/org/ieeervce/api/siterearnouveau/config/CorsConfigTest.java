package org.ieeervce.api.siterearnouveau.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.cors.CorsConfigurationSource;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CorsConfigTest {

    public static final String EXAMPLE_ORIGIN = "myorigin.origin.com";
    @Spy
    MockHttpServletRequest mockHttpServletRequest;
    @InjectMocks
    CorsConfig config;

    @Test
    void corsConfigurationSource() {
        CorsConfigurationSource originList = config.corsConfigurationSource(EXAMPLE_ORIGIN);
        assertThat(originList.getCorsConfiguration(mockHttpServletRequest).getAllowedOrigins()).singleElement().isEqualTo(EXAMPLE_ORIGIN);
    }
}