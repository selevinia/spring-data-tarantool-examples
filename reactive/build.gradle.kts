group = "io.github.selevinia.examples"

dependencies {
    implementation("io.github.selevinia:selevinia-spring-boot-starter-actuator-tarantool:0.5.0")
    implementation("io.github.selevinia:selevinia-spring-boot-starter-data-tarantool-reactive:0.5.0")

    implementation("org.springframework.boot:spring-boot-starter-webflux:3.2.4")

    compileOnly("org.projectlombok:lombok:1.18.32")
    annotationProcessor("org.projectlombok:lombok:1.18.32")

    testCompileOnly("org.projectlombok:lombok:1.18.32")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.32")

    testImplementation("org.springframework.boot:spring-boot-starter-test:3.2.4")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testImplementation("org.assertj:assertj-core:3.25.3")
    testImplementation("io.projectreactor:reactor-test:3.4.7")
    testImplementation("io.netty:netty-all:4.1.108.Final")
}