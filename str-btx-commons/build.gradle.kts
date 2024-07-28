dependencies{
    annotationProcessor(libs.lombok)
    compileOnly(libs.lombok)

    implementation(libs.spring.boot.starter.webflux)
    implementation(libs.hibernate.validator)
    implementation(libs.jackson.annotations)
    implementation(libs.jackson.databind)
    implementation(libs.reactor.core)
    implementation(libs.spring.context)
}
// Since this library is included as a jar in our jib projects, we want the
// jar to built reproducibly.
tasks.withType<Jar> {
    isPreserveFileTimestamps = false
    isReproducibleFileOrder = true
}