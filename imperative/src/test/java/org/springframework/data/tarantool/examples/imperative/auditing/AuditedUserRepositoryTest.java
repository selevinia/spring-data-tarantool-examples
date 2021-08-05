package org.springframework.data.tarantool.examples.imperative.auditing;

import io.tarantool.driver.api.conditions.Conditions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.tarantool.core.TarantoolOperations;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class AuditedUserRepositoryTest {

    @Autowired
    private AuditedUserRepository userRepository;

    @Autowired
    private TarantoolOperations operations;

    @BeforeEach
    void setUp() {
        operations.delete(Conditions.any(), AuditedUser.class);
    }

    @Test
    void shouldInsertUser() {
        AuditedUser user = user();
        user.setNew(true);

        AuditedUser saved = userRepository.save(user);
        assertThat(saved.getId()).isEqualTo(user.getId());
        assertThat(saved.getLastModifiedBy()).isEqualTo(saved.getCreatedBy())
                .isEqualTo("the-current-user");
        assertThat(saved.getLastModifiedDate()).isEqualTo(saved.getCreatedDate())
                .isBetween(LocalDateTime.now().minusMinutes(1), LocalDateTime.now().plusMinutes(1));
    }

    @Test
    void shouldUpdateUser() {
        AuditedUser user = user();
        user.setNew(true);
        userRepository.save(user);

        user.setFirstName(user.getLastName());
        user.setNew(false);

        AuditedUser saved = userRepository.save(user);
        assertThat(saved.getId()).isEqualTo(user.getId());
        assertThat(saved.getLastModifiedBy()).isEqualTo(saved.getCreatedBy())
                .isEqualTo("the-current-user");
        assertThat(saved.getCreatedDate()).isBetween(LocalDateTime.now().minusMinutes(1), LocalDateTime.now().plusMinutes(1));
        assertThat(saved.getLastModifiedDate()).isNotEqualTo(saved.getCreatedDate())
                .isBetween(LocalDateTime.now().minusMinutes(1), LocalDateTime.now().plusMinutes(1));
    }

    private AuditedUser user() {
        return AuditedUser.builder()
                .id(UUID.randomUUID())
                .firstName("Bill")
                .lastName("Evans")
                .build();
    }
}