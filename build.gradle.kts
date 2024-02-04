import org.gradle.plugins.ide.idea.model.IdeaLanguageLevel

plugins {
    id("java")
    id("idea")
    id("checkstyle")
}

idea {
    project {
        languageLevel = IdeaLanguageLevel("1.17")
    }
}

allprojects {
    apply(plugin = "java")
    apply(plugin = "checkstyle")
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }

    configurations.all {
        exclude(group = "com.google.collections", module = "google-collections")
    }
}

subprojects {
    apply(from = "${rootProject.projectDir}/gradle/dependencies.gradle.kts")

    checkstyle {
        toolVersion = "10.13.0" // Adjust this to match your Checkstyle version
        config = resources.text.fromFile("config/checkstyle/checkstyle.xml")
    }
}

val uberJar by tasks.registering(Jar::class) {
    archiveClassifier.set("uber")
    from(subprojects.map { it.tasks.named<Jar>("jar").flatMap { it.archiveFile } })
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.named("assemble").configure {
    dependsOn(uberJar)
}
