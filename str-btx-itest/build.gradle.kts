dependencies {
    implementation(project(":str-btx-in"))
    implementation(project(":str-btx-core"))

    testImplementation(libs.mockito.core)
    testImplementation(libs.spring.context)
    testImplementation(libs.spring.test)
    testImplementation(libs.spring.boot.starter.test)
    testImplementation(libs.junit)
    testImplementation(libs.jsonpath)
    testImplementation(libs.jsonassert)
}