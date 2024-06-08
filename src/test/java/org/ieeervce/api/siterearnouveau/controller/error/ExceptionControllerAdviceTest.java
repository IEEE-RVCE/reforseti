package org.ieeervce.api.siterearnouveau.controller.error;

import org.ieeervce.api.siterearnouveau.exception.DataExistsException;
import org.ieeervce.api.siterearnouveau.exception.DataNotFoundException;
import org.ieeervce.api.siterearnouveau.exception.LoginFailedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class ExceptionControllerAdviceTest {
    ExceptionControllerAdvice exceptionControllerAdvice;
    MockMvc mockMvc;
    @BeforeEach
    void setup() {
        exceptionControllerAdvice = new ExceptionControllerAdvice();
        mockMvc = MockMvcBuilders
                .standaloneSetup(new ExampleController())

                .setControllerAdvice(exceptionControllerAdvice)
                .build();
    }

    @Test
    void testNotFoundErrorHandling() throws Exception {
        mockMvc.perform(get("/notfound1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.ok", equalTo(false)))
                .andExpect(jsonPath("$.response", nullValue()));
    }
    @Test
    void testLoginErrorHandling() throws Exception {
        mockMvc.perform(get("/loginerror"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.ok", equalTo(false)))
                .andExpect(jsonPath("$.response", nullValue()));
    }

    @Test
    void testDataExists() throws Exception {
        mockMvc.perform(get("/exists"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.ok", equalTo(false)))
                .andExpect(jsonPath("$.response", nullValue()))
                .andExpect(jsonPath("$.message",equalTo("Data exists")));
    }

    @RestController
    private static class ExampleController {
        @GetMapping("/notfound1")
        void notFound() throws DataNotFoundException {
            throw new DataNotFoundException();
        }

        @GetMapping("/loginerror")
        void loginerror() throws LoginFailedException {
            throw new LoginFailedException("Failed");
        }

        @GetMapping("/exists")
        void exists() throws DataExistsException {
            throw new DataExistsException("Data exists");
        }

    }
}