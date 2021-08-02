package org.springframework.data.tarantool.examples.reactive.entitycallback;

import io.tarantool.driver.api.conditions.Conditions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.tarantool.core.ReactiveTarantoolOperations;
import reactor.test.StepVerifier;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReactiveTarantoolOperations operations;

    @BeforeEach
    void setUp() {
        operations.delete(Conditions.any(), User.class).then().as(StepVerifier::create)
                .verifyComplete();
    }

    @Test
    void shouldSaveUserWithoutId() {
        User user = User.builder()
                .firstName("Bill")
                .lastName("Evans")
                .birthDate(LocalDate.of(1929, 8, 16))
                .email("evans@gmail.com")
                .build();

        userRepository.save(user).as(StepVerifier::create)
                .assertNext(actual -> {
                    assertThat(actual.getId()).isNotNull();
                })
                .verifyComplete();
    }
}