group = "io.github.selevinia.examples"
version = "0.1.0"
description = "Sample projects for Spring Data Tarantool"

subprojects {
    apply(plugin = "java-library")

    repositories {
        mavenCentral()
    }

    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}