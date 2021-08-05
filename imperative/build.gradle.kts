group = "io.github.selevinia.examples"

dependencies {
    implementation("io.github.selevinia:selevinia-spring-boot-starter-actuator-tarantool:0.2.0")
    implementation("io.github.selevinia:selevinia-spring-boot-starter-data-tarantool:0.2.0")

    implementation("org.springframework.boot:spring-boot-starter-web:2.5.2")

    compileOnly("org.projectlombok:lombok:1.18.20")
    annotationProcessor("org.projectlombok:lombok:1.18.20")

    testCompileOnly("org.projectlombok:lombok:1.18.20")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.20")

    testImplementation("org.springframework.boot:spring-boot-starter-test:2.5.2")
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.2")
    testImplementation("org.assertj:assertj-core:3.20.2")
}