package org.springframework.data.tarantool.examples.reactive.versioned;

import org.springframework.data.tarantool.repository.ReactiveTarantoolRepository;

import java.util.UUID;

public interface VersionedUserRepository extends ReactiveTarantoolRepository<VersionedUser, UUID> {
}
