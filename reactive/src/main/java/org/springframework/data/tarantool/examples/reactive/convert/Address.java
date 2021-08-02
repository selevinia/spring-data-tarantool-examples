package org.springframework.data.tarantool.examples.reactive.convert;

import lombok.*;

@Data
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    private String city;
    private String street;
    private String postcode;
}
