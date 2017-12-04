import org.jetbrains.intellij.IntelliJPlugin
import org.jetbrains.intellij.IntelliJPluginExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.junit.platform.gradle.plugin.JUnitPlatformExtension

version = "0.8.1"

buildscript {
    var kotlin_version: String by extra

    repositories {
        mavenCentral()
    }

    dependencies {
        classpath(kotlin("gradle-plugin", kotlin_version))
        classpath("org.junit.platform:junit-platform-gradle-plugin:1.0.2")
    }

}

apply {
    plugin("java")
    plugin("kotlin")
    plugin("org.jetbrains.intellij")
    plugin("org.junit.platform.gradle.plugin")
}

plugins {
    java
}

val kotlin_version: String by extra
val junit_version: String by extra
val jmockit_version: String by extra
val assertj_version: String by extra
val mockito_version: String by extra

repositories {
    mavenCentral()
    maven {
        setUrl("http://dl.bintray.com/jetbrains/spek")
    }
}

dependencies {
    compile(kotlin("stdlib-jdk8", kotlin_version))
    compile(project(":common"))
    compile(project(":lang"))

    testCompile(project(":test-framework"))
    testCompile("org.jetbrains.spek:spek-api:1.1.5")
    testRuntime("org.jetbrains.spek:spek-junit-platform-engine:1.1.5")
    testCompile("org.junit.jupiter:junit-jupiter-api:5.0.2")
    testRuntime("org.junit.jupiter:junit-jupiter-engine:5.0.2")
    testRuntime("org.junit.vintage:junit-vintage-engine:4.12.2")
    testCompile("org.mockito:mockito-core:$mockito_version")
    testCompile("org.jmockit:jmockit:$jmockit_version")
    testCompile("org.assertj:assertj-core:$assertj_version")
    testCompile("junit", "junit", junit_version)
}

configure<JUnitPlatformExtension> {
    platformVersion = "1.0.2"
    filters {
        engines {
            include(
                    "junit-jupiter",
                    "junit-vintage",
                    "spek"
            )
        }
    }
}

configure<IntelliJPluginExtension> {
    version = "2017.2.5"
    setPlugins(
            "IntelliLang"
    )
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

