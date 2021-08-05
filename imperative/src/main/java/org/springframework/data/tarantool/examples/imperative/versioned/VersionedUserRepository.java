package org.springframework.data.tarantool.examples.imperative.versioned;

import org.springframework.data.tarantool.repository.TarantoolRepository;

import java.util.UUID;

public interface VersionedUserRepository extends TarantoolRepository<VersionedUser, UUID> {
}
