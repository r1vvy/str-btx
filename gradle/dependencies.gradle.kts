object Versions {
    const val springBoot = "3.2.1"
    const val reactor = "3.6.1"
    const val prometheus = "1.12.2"

    /* tests */
    const val junit = "4.13.1"
    const val mockito = "5.9.0"
    const val wiremock = "3.3.1"

    /* utility */
    const val jackson = "2.16.0"
    const val lombok = "1.18.30"
    const val validation = "3.0.2"

    /* logging */
    const val slf4jApi = "2.0.9"
    const val logback = "1.4.14"
    const val googleCloudLogging = "3.15.16"
}

object Deps {
    object Reactor {
        const val reactorCore = "io.projectreactor:reactor-core:${Versions.reactor}"
    }
    object SpringBoot {
        const val springBootStarterActuator = "org.springframework.boot:spring-boot-starter-actuator:${Versions.springBoot}"
        const val springBootStarter = "org.springframework.boot:spring-boot-starter:${Versions.springBoot}"
        const val springBootTestStarter = "org.springframework.boot:spring-boot-starter-test:${Versions.springBoot}"
        const val autoconfigure = "org.springframework.boot:spring-boot-autoconfigure:${Versions.springBoot}"
        const val webFlux = "org.springframework.boot:spring-boot-starter-webflux:${Versions.springBoot}"
    }
    object Jackson {
        const val jacksonAnnotations = "com.fasterxml.jackson.core:jackson-annotations:${Versions.jackson}"
        const val jacksonDatabind = "com.fasterxml.jackson.core:jackson-databind:${Versions.jackson}"
    }
    object Logging {
        const val slf4j = "org.slf4j:slf4j-api:${Versions.slf4jApi}"
        const val google = "com.google.cloud:google-cloud-logging:${Versions.googleCloudLogging}"
        const val logback = "ch.qos.logback:logback-classic:${Versions.logback}"
    }
    object Test {
        const val junit = "junit:junit:${Versions.junit}"
        const val mockito = "org.mockito:mockito-core:${Versions.mockito}"
        const val reactorTest = "io.projectreactor:reactor-test:${Versions.reactor}"
        const val wiremock = "org.wiremock:wiremock:${Versions.wiremock}"
    }
    object Utilities {
        const val lombok = "org.projectlombok:lombok:${Versions.lombok}"
        const val validation = "jakarta.validation:jakarta.validation-api:${Versions.validation}"
    }
    object Metrics {
        const val prometheus = "io.micrometer:micrometer-registry-prometheus:${Versions.prometheus}"
    }
}
