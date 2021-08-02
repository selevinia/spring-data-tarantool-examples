package org.springframework.data.tarantool.examples.reactive.simple;

import io.tarantool.driver.api.conditions.Conditions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.tarantool.core.ReactiveTarantoolOperations;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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
    void shouldSaveAndFindUser() {
        User user = user();
        userRepository.save(user).as(StepVerifier::create)
                .expectNext(user)
                .verifyComplete();
        userRepository.findById(user.getId()).as(StepVerifier::create)
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    void shouldCountUsersByLastName() {
        userRepository.saveAll(List.of(user(), user(), user())).then().as(StepVerifier::create)
                .verifyComplete();
        userRepository.countUsersByLastName("Evans").as(StepVerifier::create)
                .expectNext(3L)
                .verifyComplete();
    }

    @Test
    void shouldFindUsersByLastName() {
        userRepository.saveAll(List.of(user(), user(), user())).then().as(StepVerifier::create)
                .verifyComplete();
        userRepository.findAllUsersByLastName("Evans").as(StepVerifier::create)
                .expectNextCount(3L)
                .verifyComplete();
    }

    @Test
    void shouldFindUsersByFirstNameAndLastName() {
        User user = user();
        userRepository.save(user).then().as(StepVerifier::create)
                .verifyComplete();
        userRepository.findAllByFirstNameAndLastName(user.getFirstName(), user.getLastName()).as(StepVerifier::create)
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    void shouldFindUsersByBirthDate() {
        User user = user();
        userRepository.save(user).then().as(StepVerifier::create)
                .verifyComplete();
        userRepository.findAllByBirthDate(user.getBirthDate()).as(StepVerifier::create)
                .expectNext(user)
                .verifyComplete();
    }

    private User user() {
        return User.builder()
                .id(UUID.randomUUID())
                .firstName("Bill")
                .lastName("Evans")
                .birthDate(LocalDate.of(1929, 8, 16))
                .email("evans@gmail.com")
                .build();
    }
}