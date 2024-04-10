group = "io.github.selevinia.examples"

dependencies {
    implementation("io.github.selevinia:selevinia-spring-boot-starter-actuator-tarantool:0.5.0")
    implementation("io.github.selevinia:selevinia-spring-boot-starter-cache-tarantool:0.5.0")

    implementation("org.springframework.boot:spring-boot-starter-web:3.2.4")

    compileOnly("org.projectlombok:lombok:1.18.32")
    annotationProcessor("org.projectlombok:lombok:1.18.32")

    testCompileOnly("org.projectlombok:lombok:1.18.32")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.32")

    testImplementation("org.springframework.boot:spring-boot-starter-test:3.2.4")
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testImplementation("org.assertj:assertj-core:3.25.3")
}