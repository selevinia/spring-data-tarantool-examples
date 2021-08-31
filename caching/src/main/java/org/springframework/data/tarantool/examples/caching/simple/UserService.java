package org.springframework.data.tarantool.examples.caching.simple;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "users")
public class UserService {

    private final UserRepository userRepository;

    @Cacheable
    public User getUser(UUID id) {
        return userRepository.findById(id).orElse(null);
    }

    @CachePut(key = "#user.id")
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @CacheEvict(key = "#user.id")
    public void deleteUser(User user) {
        userRepository.delete(user);
    }
}
