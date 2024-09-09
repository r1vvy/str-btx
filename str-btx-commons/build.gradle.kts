dependencies{
    annotationProcessor(libs.lombok)
    compileOnly(libs.lombok)

    implementation(libs.spring.boot.starter.webflux)
    implementation(libs.spring.boot.starter.validation)
    implementation(libs.jackson.databind)
    implementation(libs.reactor.core)
    implementation(libs.spring.context)

    testImplementation(libs.spring.test)
    testImplementation(libs.junitApi)
    testImplementation(libs.junitEngine)
    testImplementation(libs.reactor.test)
    testImplementation(libs.mockitoJunit)
}
// Since this library is included as a jar in our jib projects, we want the
// jar to built reproducibly.
tasks.withType<Jar> {
    isPreserveFileTimestamps = false
    isReproducibleFileOrder = true
}