package com.vedubox.notificationservice.controller;

import com.vedubox.notificationservice.repository.TemplateRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.logging.Logger;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Another useful approach is to not start the server at all but to test only the layer below that,
 * where Spring handles the incoming HTTP request and hands it off to your controller.
 * That way, almost of the full stack is used, and your code will be called in exactly the same way as if
 * it were processing a real HTTP request but without the cost of starting the server.
 * To do that, use Spring’s MockMvc and ask for that to be injected for you by using
 * the '@AutoConfigureMockMvc' annotation on the test case.
 *
 * In this test, the full Spring application context is started but without the server.
 * We can narrow the tests to only the web layer by using '@WebMvcTest', as the following listing
 */
@WebMvcTest(HealthCheckController.class)
public class HealthCheckControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private Logger log;
    @MockBean private TemplateRepository templateRepository;

    @Value("${spring.application.name:Application name not defined.}")
    private String springApplicationName;

    @Test
    public void givenTheApplicationIsRunning_whenErrorCalledWithoutCredentials_then200WithApplicationName() throws Exception {
        mockMvc.perform(get("/public/api/health-check/error"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(is(springApplicationName)));
    }

    @Test
    public void givenTheApplicationIsRunning_whenWarnCalledWithoutCredentials_then200WithApplicationName() throws Exception {
        mockMvc.perform(get("/public/api/health-check/warn"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(is(springApplicationName)));
    }

    @Test
    public void givenTheApplicationIsRunning_whenInfoCalledWithoutCredentials_then200WithApplicationName() throws Exception {
        mockMvc.perform(post("/public/api/health-check/info"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(content().string(is(springApplicationName)));
    }
}
