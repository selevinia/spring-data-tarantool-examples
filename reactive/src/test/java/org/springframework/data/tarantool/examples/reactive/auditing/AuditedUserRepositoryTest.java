package org.springframework.data.tarantool.examples.reactive.auditing;

import io.tarantool.driver.api.conditions.Conditions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.tarantool.core.ReactiveTarantoolOperations;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class AuditedUserRepositoryTest {

    @Autowired
    private AuditedUserRepository userRepository;

    @Autowired
    private ReactiveTarantoolOperations operations;

    @BeforeEach
    void setUp() {
        operations.delete(Conditions.any(), AuditedUser.class).then().as(StepVerifier::create)
                .verifyComplete();
    }

    @Test
    void shouldInsertUser() {
        AuditedUser user = user();
        user.setNew(true);
        userRepository.save(user).as(StepVerifier::create)
                .assertNext(actual -> {
                    assertThat(actual.getId()).isEqualTo(user.getId());
                    assertThat(actual.getLastModifiedBy()).isEqualTo(actual.getCreatedBy())
                            .isEqualTo("the-current-user");
                    assertThat(actual.getLastModifiedDate()).isEqualTo(actual.getCreatedDate())
                            .isBetween(LocalDateTime.now().minusMinutes(1), LocalDateTime.now().plusMinutes(1));
                })
                .verifyComplete();
    }

    @Test
    void shouldUpdateUser() {
        AuditedUser user = user();
        user.setNew(true);
        userRepository.save(user).then().as(StepVerifier::create)
                .verifyComplete();

        user.setFirstName(user.getLastName());
        user.setNew(false);

        userRepository.save(user).as(StepVerifier::create)
                .assertNext(actual -> {
                    assertThat(actual.getId()).isEqualTo(user.getId());
                    assertThat(actual.getLastModifiedBy()).isEqualTo(actual.getCreatedBy())
                            .isEqualTo("the-current-user");
                    assertThat(actual.getCreatedDate()).isBetween(LocalDateTime.now().minusMinutes(1), LocalDateTime.now().plusMinutes(1));
                    assertThat(actual.getLastModifiedDate()).isNotEqualTo(actual.getCreatedDate())
                            .isBetween(LocalDateTime.now().minusMinutes(1), LocalDateTime.now().plusMinutes(1));
                })
                .verifyComplete();
    }

    private AuditedUser user() {
        return AuditedUser.builder()
                .id(UUID.randomUUID())
                .firstName("Bill")
                .lastName("Evans")
                .build();
    }
}