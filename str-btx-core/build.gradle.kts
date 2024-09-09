dependencies {
    annotationProcessor(libs.lombok)
    compileOnly(libs.lombok)

    implementation(libs.spring.context)
    implementation(libs.slf4j.api)
    implementation(libs.reactor.core)

    implementation(libs.spring.boot.starter.webflux)
    implementation(libs.google.api.sheets)
    implementation(project(":str-btx-commons"))
    implementation(project(":str-btx-out"))
}
// Since this library is included as a jar in our jib projects, we want the
// jar to built reproducibly.
tasks.withType<Jar> {
    isPreserveFileTimestamps = false
    isReproducibleFileOrder = true
}