plugins {
    id("com.google.cloud.tools.jib") version "3.1.4"
}

dependencies {
    implementation(libs.slf4j.api)
    implementation(libs.logback.classic)
    implementation(libs.spring.boot.starter)
    implementation("com.google.cloud:google-cloud-logging-logback:0.131.5-alpha")

    implementation(project(":str-btx-in"))
    implementation(project(":str-btx-out"))
    implementation(project(":str-btx-core"))
    implementation(project(":str-btx-commons"))
}

jib {
    container {
        creationTime = "USE_CURRENT_TIMESTAMP"
    }
    //to {
      //  image = "europe-north1-docker.pkg.dev/straujupite-project/straujupite-api/str-btx-api:v1"
    //}
    from {
        image = "openjdk:17-jdk-slim"
    }
}