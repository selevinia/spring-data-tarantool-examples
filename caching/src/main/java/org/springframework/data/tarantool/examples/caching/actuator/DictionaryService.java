package org.springframework.data.tarantool.examples.caching.actuator;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DictionaryService {

    @Cacheable("dictionary")
    public String getValue(String code) {
        return code;
    }

    @CacheEvict("dictionary")
    public void removeValue(String code) {
    }
}
