import org.gradle.plugins.ide.idea.model.IdeaLanguageLevel

plugins {
    id("java")
    id("idea")
    id("checkstyle")
    id("jacoco")
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

    checkstyle {
        toolVersion = "10.13.0" // Adjust this to match your Checkstyle version
        config = resources.text.fromFile("config/checkstyle/checkstyle.xml")
    }

    // for checkstyle
    configurations.all {
        exclude(group = "com.google.collections", module = "google-collections")
    }
}

val uberJar by tasks.registering(Jar::class) {
    archiveClassifier.set("uber")
    from(subprojects.map { it.tasks.named<Jar>("jar").flatMap { it.archiveFile } })
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

tasks.named("build").configure {
    dependsOn(tasks.jacocoTestReport)
}

tasks.named("assemble").configure {
    dependsOn(uberJar)
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn("test")
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}
