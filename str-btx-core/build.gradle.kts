dependencies {
    annotationProcessor(libs.lombok)
    compileOnly(libs.lombok)

    implementation(libs.spring.context)
    implementation(libs.slf4j.api)
    implementation(libs.reactor.core)
}