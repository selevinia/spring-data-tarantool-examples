group = "io.github.selevinia.examples"
version = "0.5.0"
description = "Sample projects for Spring Data Tarantool"

subprojects {
    apply(plugin = "java-library")

    repositories {
        mavenLocal()
        mavenCentral()
    }

    configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    tasks.withType<JavaCompile> {
        options.compilerArgs.add("-parameters")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}