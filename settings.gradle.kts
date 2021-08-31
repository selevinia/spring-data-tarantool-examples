rootProject.name = "spring-data-tarantool-examples"

include("imperative")
project(":imperative").name = "spring-data-tarantool-imperative"

include("reactive")
project(":reactive").name = "spring-data-tarantool-reactive"

include("caching")
project(":caching").name = "spring-data-tarantool-caching"
