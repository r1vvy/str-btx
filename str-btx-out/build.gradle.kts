dependencies {
    annotationProcessor(libs.lombok)
    compileOnly(libs.lombok)

    implementation(project(":str-btx-commons"))

    implementation(libs.reactor.core)
    implementation(libs.spring.boot.starter.webflux)
    implementation(libs.google.api.client)
    implementation(libs.google.api.sheets)
    implementation(libs.google.oauth)

    testImplementation(libs.junitApi)
    testImplementation(libs.junitEngine)
    testImplementation(libs.mockito.core)
    testImplementation(libs.reactor.test)
    testImplementation(libs.mockitoJunit)

}
// Since this library is included as a jar in our jib projects, we want the
// jar to built reproducibly.
tasks.withType<Jar> {
    isPreserveFileTimestamps = false
    isReproducibleFileOrder = true
}