dependencies {
    annotationProcessor(libs.lombok)
    compileOnly(libs.lombok)

    implementation(project(":str-btx-commons"))

    implementation(libs.reactor.core)
    implementation(libs.spring.boot.starter.webflux)

    testImplementation(libs.junit)
    testImplementation(libs.mockito.core)
    testImplementation(libs.reactor.test)

}
