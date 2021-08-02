package org.springframework.data.tarantool.examples.reactive.actuator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthComponent;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.test.web.reactive.server.JsonPathAssertions;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT, classes = {ApplicationConfiguration.class, ActuatorTest.TestConfig.class})
public class ActuatorTest {

    @Configuration
    protected static class TestConfig {
        @Bean
        public WebClient webClient() {
            HttpClient httpClient = HttpClient.create();
            ClientHttpConnector connector = new ReactorClientHttpConnector(httpClient);
            return WebClient.builder()
                    .clientConnector(connector)
                    .build();
        }
    }

    @LocalServerPort
    private int serverPort;

    @Autowired
    private WebClient webClient;

    @Test
    void shouldCheckHealthIsUp() {
        webClient.get()
                .uri(String.format("localhost:%s/actuator/health", serverPort))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .as(StepVerifier::create)
                .assertNext(health -> {
                    assertThat(health).contains("\"status\":\"UP\"");
                    assertThat(health).contains("Tarantool 2.9.0");
                })
                .verifyComplete();
    }
}
