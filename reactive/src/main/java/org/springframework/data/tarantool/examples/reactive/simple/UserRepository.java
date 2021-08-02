package org.springframework.data.tarantool.examples.reactive.simple;

import org.springframework.data.tarantool.repository.Query;
import org.springframework.data.tarantool.repository.ReactiveTarantoolRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;

public interface UserRepository extends ReactiveTarantoolRepository<User, UUID> {

    Flux<User> findAllByBirthDate(LocalDate birthDate);

    Flux<User> findAllByFirstNameAndLastName(String firstName, String lastName);

    @Query(function = "find_users_by_last_name")
    Flux<User> findAllUsersByLastName(String lastName);

    @Query(function = "count_users_by_last_name")
    Mono<Long> countUsersByLastName(String lastName);
}
