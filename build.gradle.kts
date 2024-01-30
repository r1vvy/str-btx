import org.gradle.plugins.ide.idea.model.IdeaLanguageLevel

plugins {
    id("java")
    id("idea")
}

idea {
    project {
        languageLevel = IdeaLanguageLevel("1.17")
    }
}

allprojects {
    apply(plugin = "java")
    version = "1.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(from = "${rootProject.projectDir}/gradle/dependencies.gradle.kts")
}
