dependencies {
    implementation(libs.slf4j.api)
    implementation(libs.logback.classic)
    implementation(libs.spring.boot.starter)

    implementation(project(":str-btx-in"))
    implementation(project(":str-btx-out"))
    implementation(project(":str-btx-core"))
    implementation(project(":str-btx-commons"))
}