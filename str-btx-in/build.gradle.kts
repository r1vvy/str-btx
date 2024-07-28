dependencies {
    annotationProcessor(libs.lombok)
    compileOnly(libs.lombok)

    implementation(libs.hibernate.validator)
    implementation(libs.reactor.core)
    implementation(libs.spring.boot.starter.webflux)
    implementation(project(":str-btx-core"))
    implementation(project(":str-btx-commons"))
}
// Since this library is included as a jar in our jib projects, we want the
// jar to built reproducibly.
tasks.withType<Jar> {
    isPreserveFileTimestamps = false
    isReproducibleFileOrder = true
}