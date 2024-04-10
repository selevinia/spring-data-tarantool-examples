package org.springframework.data.tarantool.examples.caching.actuator;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT,
        classes = {ApplicationConfiguration.class, ActuatorTest.TestConfig.class},
        properties = {"selevinia.cache.tarantool.cache-names=dictionary", "selevinia.cache.tarantool.enable-statistics=true"})
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

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    void setUp() {
        Objects.requireNonNull(cacheManager.getCache("dictionary")).clear();
    }

    @Test
    void shouldCheckHealthIsUp() {
        ResponseEntity<String> response = restTemplate.getForEntity(String.format("http://localhost:%s/actuator/health", serverPort), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("\"status\":\"UP\"");
        assertThat(response.getBody()).contains("Tarantool 2.11.2");
    }

    @Test
    @SneakyThrows
    void shouldCheckCacheInfo() {
        dictionaryService.getValue("1");

        ResponseEntity<String> response = restTemplate.getForEntity(String.format("http://localhost:%s/actuator/caches", serverPort), String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("\"cacheManager\"");
        assertThat(response.getBody()).contains("\"cached_dictionary\"");
        assertThat(response.getBody()).contains("\"org.springframework.data.tarantool.cache.DefaultTarantoolCacheWriter\"");
    }

    @Test
    @SneakyThrows
    void shouldCheckCacheMetrics() {
        dictionaryService.getValue("1");
        dictionaryService.getValue("1");
        dictionaryService.getValue("1");
        dictionaryService.removeValue("1");

        ResponseEntity<String> responseForGets = restTemplate.getForEntity(String.format("http://localhost:%s/actuator/metrics/cache.gets?tag=name:cached_dictionary", serverPort), String.class);
        assertThat(responseForGets.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseForGets.getBody()).contains("\"COUNT\",\"value\":3.0");

        ResponseEntity<String> responseForPuts = restTemplate.getForEntity(String.format("http://localhost:%s/actuator/metrics/cache.puts?tag=name:cached_dictionary", serverPort), String.class);
        assertThat(responseForPuts.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseForPuts.getBody()).contains("\"COUNT\",\"value\":1.0");

        ResponseEntity<String> responseForRemovals = restTemplate.getForEntity(String.format("http://localhost:%s/actuator/metrics/cache.removals?tag=name:cached_dictionary", serverPort), String.class);
        assertThat(responseForRemovals.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseForRemovals.getBody()).contains("\"COUNT\",\"value\":1.0");
    }
}
