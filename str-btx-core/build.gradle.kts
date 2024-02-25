dependencies {
    annotationProcessor(libs.lombok)
    compileOnly(libs.lombok)

    implementation(libs.spring.context)
    implementation(libs.slf4j.api)
    implementation(libs.reactor.core)

    implementation(libs.spring.boot.starter.webflux)
    implementation(project(":str-btx-commons"))
    implementation(project(":str-btx-out"))
}