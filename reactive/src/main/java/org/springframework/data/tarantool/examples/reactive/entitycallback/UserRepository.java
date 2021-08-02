package org.springframework.data.tarantool.examples.reactive.entitycallback;

import org.springframework.data.tarantool.repository.ReactiveTarantoolRepository;

import java.util.UUID;

public interface UserRepository extends ReactiveTarantoolRepository<User, UUID> {
}
