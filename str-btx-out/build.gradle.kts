dependencies {
    annotationProcessor(libs.lombok)
    compileOnly(libs.lombok)

    implementation(libs.reactor.core)
    implementation(libs.spring.boot.starter.webflux)
    implementation(project(":str-btx-core"))
}