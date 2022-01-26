package com.vedubox.notificationservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The '@SpringBootTest' annotation tells Spring Boot to look for a main configuration class
 * (one with '@SpringBootApplication', for instance) and use that to start a Spring application context.
 * You can run this test in your IDE or on the command line (by running ./mvnw test or ./gradlew test), and it should pass.
 * To convince yourself that the context is creating your controller, you could add an assertion, as the following example:
 *
 * A nice feature of the Spring Test support is that the application context is cached between tests.
 * That way, if you have multiple methods in a test case or multiple test cases with the same configuration,
 * they incur the cost of starting the application only once. You can control the cache by using the @DirtiesContext annotation.
 */
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NotificationServiceApplicationTests {

	@LocalServerPort private int port;
	@Autowired private MockMvc mockMvc;

	@Value("${spring.application.name:Application name not defined.}")
	private String springApplicationName;

	@Test
	public void contextLoads() throws Exception {
		mockMvc.perform(get("http://localhost:" + port + "/public/api/health-check/error"))
				.andExpect(status().isOk())
				.andExpect(content().string(is(springApplicationName)));
	}
}
