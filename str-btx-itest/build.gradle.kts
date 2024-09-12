dependencies {
    implementation(project(":str-btx-in"))
    implementation(project(":str-btx-out"))
    implementation(project(":str-btx-commons"))
    implementation(project(":str-btx-core"))

    testImplementation(libs.mockito.core)
    testImplementation(libs.spring.context)
    testImplementation(libs.spring.test)
    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.junitApi)
    testImplementation(libs.jsonpath)
    testImplementation(libs.jsonassert)
    testImplementation(libs.wiremock)
    testImplementation(libs.reactor.test)
    testImplementation(libs.google.api.client)
    testImplementation(libs.google.api.sheets)
    testImplementation(libs.google.oauth)

    testImplementation(libs.junitEngine)
    testRuntimeOnly(libs.junitEngine)
}