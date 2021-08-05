package org.springframework.data.tarantool.examples.reactive.versioned;

import io.tarantool.driver.api.conditions.Conditions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.tarantool.core.ReactiveTarantoolOperations;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class VersionedUserRepositoryTest {

    @Autowired
    private VersionedUserRepository userRepository;

    @Autowired
    private ReactiveTarantoolOperations operations;

    @BeforeEach
    void setUp() {
        operations.delete(Conditions.any(), VersionedUser.class).then().as(StepVerifier::create)
                .verifyComplete();
    }

    @Test
    void shouldInsertUser() {
        VersionedUser user = user();
        userRepository.save(user).as(StepVerifier::create)
                .assertNext(actual -> {
                    assertThat(actual.getId()).isEqualTo(user.getId());
                    assertThat(actual.getVersion()).isEqualTo(0);
                })
                .verifyComplete();
    }

    @Test
    void shouldUpdateUser() {
        VersionedUser user = user();
        userRepository.save(user).then().as(StepVerifier::create)
                .verifyComplete();

        VersionedUser updated = user();
        updated.setId(user.getId());
        updated.setFirstName(user.getLastName());
        userRepository.save(user).as(StepVerifier::create)
                .assertNext(actual -> {
                    assertThat(actual.getId()).isEqualTo(user.getId());
                    assertThat(actual.getVersion()).isEqualTo(1);
                })
                .verifyComplete();
    }

    private VersionedUser user() {
        return VersionedUser.builder()
                .id(UUID.randomUUID())
                .firstName("Bill")
                .lastName("Evans")
                .build();
    }
}