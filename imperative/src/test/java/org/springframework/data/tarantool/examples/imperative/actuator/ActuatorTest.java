package org.springframework.data.tarantool.examples.imperative.actuator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT, classes = {ApplicationConfiguration.class, ActuatorTest.TestConfig.class})
public class ActuatorTest {

    @Configuration
    protected static class TestConfig {
        @Bean
        public RestTemplate restTemplate() {
            return new RestTemplate();
        }
    }

    @LocalServerPort
    private int serverPort;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    void shouldCheckHealthIsUp() {
        ResponseEntity<String> response = restTemplate.getForEntity(String.format("http://localhost:%s/actuator/health", serverPort), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("\"status\":\"UP\"");
        assertThat(response.getBody()).contains("Tarantool 2.9.0");
    }
}
