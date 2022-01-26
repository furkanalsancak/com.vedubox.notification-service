package com.vedubox.notificationservice.controller;

import com.vedubox.commonlibraries.model.enums.Language;
import com.vedubox.commonlibraries.model.enums.MessageType;
import com.vedubox.notificationservice.domain.Template;
import com.vedubox.notificationservice.model.NotificationType;
import com.vedubox.notificationservice.repository.TemplateRepository;
import com.vedubox.notificationservice.service.TemplateService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.logging.Logger;

import java.net.URI;

import static com.vedubox.commonlibraries.model.enums.HeaderName.X_CORRELATION_ID;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
@WebMvcTest(TemplateController.class)
public class TemplateControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private Logger log;
    @MockBean private TemplateService templateService;
    @MockBean private TemplateRepository templateRepository;

    @Value("${spring.security.user.name}")
    private String springSecurityUserName;
    @Value("${spring.security.user.password}")
    private String springSecurityUserPassword;

    private static final String CORRELATION_ID = "correlationId";

    @Test
    public void givenTheApplicationIsRunning_whenGetAllCalledWithoutCredentials_then401() throws Exception {
        // When Then
        mockMvc.perform(
                get(new URI("/api/v1/templates/all"))
                        .contentType(APPLICATION_JSON_VALUE)
                        .header(X_CORRELATION_ID.toString(), CORRELATION_ID))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.XCorrelationId", is(CORRELATION_ID)));
    }

    @Test
    public void givenTheApplicationIsRunning_whenGetCalledWithoutCredentials_then401() throws Exception {
        // When Then
        mockMvc.perform(
                get(new URI("/api/v1/templates"))
                        .contentType(APPLICATION_JSON_VALUE)
                        .header("x-correlation-id", CORRELATION_ID))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.XCorrelationId", is(CORRELATION_ID)));
    }

    @Test
    public void givenTheApplicationIsRunning_whenPostCalledWithoutCredentials_then401() throws Exception {
        // When Then
        mockMvc.perform(
                post(new URI("/api/v1/templates"))
                        .contentType(APPLICATION_JSON_VALUE)
                        .header("x-correlation-id", CORRELATION_ID))
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.XCorrelationId", is(CORRELATION_ID)));
    }

    @Test
    public void givenTheApplicationIsRunning_whenGetAllCalledWithCredentials_then200() throws Exception {
        // Given
        String credentials = springSecurityUserName + ":" + springSecurityUserPassword;
        String authHeader = new String(Base64.encodeBase64(credentials.getBytes()));
        List<Template> templates = List.of(
                new Template() {{
                    setApplicationId(0);
                    setNotificationType(NotificationType.EMAIL);
                    setMessageType(MessageType.LIVELESSON_CANCELLED_EVENT);
                    setLanguage(Language.EN);
                    setMessage("test-message");
                }}
        );

        when(templateService.getAll(anyString())).thenReturn(templates);

        // When
        mockMvc.perform(
                get(new URI("/api/v1/templates/all"))
                    .contentType(APPLICATION_JSON_VALUE)
                    .accept(APPLICATION_JSON_VALUE)
                    .header("x-correlation-id", CORRELATION_ID)
                    .header("Authorization", "Basic " + authHeader))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.Data[0].Message", is("test-message")));

        // Then
        verify(templateService, times(1))
                .getAll(eq(CORRELATION_ID));
    }

}
