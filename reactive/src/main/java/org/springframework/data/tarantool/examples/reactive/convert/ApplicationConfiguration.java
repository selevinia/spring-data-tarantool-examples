package org.springframework.data.tarantool.examples.reactive.convert;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.tarantool.core.convert.TarantoolCustomConversions;
import org.springframework.util.StringUtils;

import java.util.List;

@SpringBootApplication
public class ApplicationConfiguration {

    @Bean
    public TarantoolCustomConversions tarantoolCustomConversions() {
        return new TarantoolCustomConversions(List.of(
                new AddressWriteConverter(),
                new AddressReadConverter()
        ));
    }

    @WritingConverter
    static class AddressWriteConverter implements Converter<Address, String> {

        @SneakyThrows
        public String convert(Address source) {
            return new ObjectMapper().writeValueAsString(source);
        }
    }

    @ReadingConverter
    static class AddressReadConverter implements Converter<String, Address> {

        @SneakyThrows
        public Address convert(String source) {
            if (StringUtils.hasText(source)) {
                return new ObjectMapper().readValue(source, Address.class);
            }
            return null;
        }
    }
}
