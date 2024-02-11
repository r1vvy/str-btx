dependencies {
    annotationProcessor(libs.lombok)
    compileOnly(libs.lombok)

    implementation(libs.hibernate.validator)
    implementation(libs.jackson.annotations)
    implementation(libs.jackson.databind)
    implementation(libs.reactor.core)
    implementation(libs.spring.context)

}