package org.springframework.data.tarantool.examples.caching.customized;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Cacheable(cacheNames = "short_term_users", key = "'st-' + #id")
    public User getShortTermUser(UUID id) {
        return userRepository.findById(id).orElse(null);
    }

    @Cacheable(cacheNames = "long_term_users", key = "'lt-' + #id")
    public User getLongTermUser(UUID id) {
        return userRepository.findById(id).orElse(null);
    }
}
