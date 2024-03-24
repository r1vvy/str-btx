import org.gradle.plugins.ide.idea.model.IdeaLanguageLevel

plugins {
    id("java")
    id("idea")
//    id("checkstyle")
    id("jacoco")
    id("org.springframework.boot") version "3.2.2" apply false
}

idea {
    project {
        languageLevel = IdeaLanguageLevel("1.17")
    }
}

allprojects {
    apply(plugin = "java")
    //    apply(plugin = "checkstyle")
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }

    // for checkstyle
    configurations.all {
        exclude(group = "com.google.collections", module = "google-collections")
    }

    val uberJar by tasks.registering(Jar::class) {
        archiveClassifier.set("uber")
        from(subprojects.map { it.tasks.named<Jar>("jar").flatMap { it.archiveFile } })
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }

    //checkstyle {
//    toolVersion = "10.13.0"
//    config = resources.text.fromFile("/config/checkstyle/checkstyle.xml")
//}

    tasks.named("assemble").configure {
        dependsOn(uberJar)
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport)
    jvmArgs("-Dfile.encoding=UTF-8")
}

tasks.jacocoTestReport {
    dependsOn("test")
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}

tasks.named("build").configure {
    dependsOn(tasks.jacocoTestReport)
}
