dependencies {
    annotationProcessor(libs.lombok)
    compileOnly(libs.lombok)

    implementation(libs.reactor.core)
    implementation(libs.spring.boot.starter.webflux)
    implementation(libs.junit)
    implementation(libs.mockito.core)
    implementation(libs.reactor.test)
    implementation(project(":str-btx-commons"))
}
