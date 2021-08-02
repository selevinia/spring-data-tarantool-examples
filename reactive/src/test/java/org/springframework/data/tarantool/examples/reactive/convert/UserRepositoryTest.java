package org.springframework.data.tarantool.examples.reactive.convert;

import io.tarantool.driver.api.conditions.Conditions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.tarantool.core.ReactiveTarantoolOperations;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.UUID;

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
    void shouldSaveAndFindUserWithAddress() {
        User user = User.builder()
                .id(UUID.randomUUID())
                .firstName("Bill")
                .lastName("Evans")
                .birthDate(LocalDate.of(1929, 8, 16))
                .address(Address.builder()
                        .city("Paris")
                        .street("33 Rue de Tlemcen")
                        .postcode("75020")
                        .build())
                .build();
        userRepository.save(user).as(StepVerifier::create)
                .expectNext(user)
                .verifyComplete();
        userRepository.findById(user.getId()).as(StepVerifier::create)
                .assertNext(actual -> {
                    assertThat(actual.getId()).isEqualTo(user.getId());
                    assertThat(actual.getAddress()).isEqualTo(user.getAddress());
                })
                .verifyComplete();
    }

    @Test
    void shouldSaveAndFindUserWithoutAddress() {
        User user = User.builder()
                .id(UUID.randomUUID())
                .firstName("Bill")
                .lastName("Evans")
                .birthDate(LocalDate.of(1929, 8, 16))
                .build();
        userRepository.save(user).as(StepVerifier::create)
                .expectNext(user)
                .verifyComplete();
        userRepository.findById(user.getId()).as(StepVerifier::create)
                .assertNext(actual -> {
                    assertThat(actual.getId()).isEqualTo(user.getId());
                    assertThat(actual.getAddress()).isNull();
                })
                .verifyComplete();
    }
}