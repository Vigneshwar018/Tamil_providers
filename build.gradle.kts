import com.lagradost.cloudstream3.gradle.CloudstreamExtension
import com.android.build.gradle.BaseExtension
buildscript {
    repositories {
        google()
        mavenCentral()
        // JitPack repo which contains your tools and dependencies
        maven("https://jitpack.io")
    }

    dependencies {
        classpath("com.android.tools.build:gradle:7.0.4")
        // Cloudstream gradle plugin
        classpath("com.github.recloudstream:gradle:master-SNAPSHOT")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0")
    }
}

// Move the plugins block to the top level
plugins {
    id("org.jetbrains.kotlin.jvm") version "1.9.0"

}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

// Custom functions for your project
fun Project.cloudstream(configuration: CloudstreamExtension.() -> Unit) = extensions.getByName<CloudstreamExtension>("cloudstream").configuration()

fun Project.android(configuration: BaseExtension.() -> Unit) = extensions.getByName<BaseExtension>("android").configuration()

subprojects {
    apply(plugin = "com.android.library")
    apply(plugin = "kotlin-android")
    apply(plugin = "com.lagradost.cloudstream3.gradle")

    cloudstream {
        setRepo(System.getenv("GITHUB_REPOSITORY") ?: "https://github.com/LikDev-256/likdev256-tamil-providers")
    }

    android {
        compileSdkVersion(33)

        defaultConfig {
            minSdk = 21
            targetSdk = 33
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }

        tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
            kotlinOptions {
                jvmTarget = "1.8" // Required
                freeCompilerArgs = freeCompilerArgs +
                        "-Xno-call-assertions" +
                        "-Xno-param-assertions" +
                        "-Xno-receiver-assertions"
            }
        }
    }

    dependencies {
        val apk by configurations
        val implementation by configurations

        // Stubs for all Cloudstream classes
        apk("com.lagradost:cloudstream3:pre-release")

        // Other dependencies
        implementation(kotlin("stdlib"))
        implementation("com.github.Blatzar:NiceHttp:0.3.2")
        implementation("org.jsoup:jsoup:1.15.3")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.1")
        implementation("me.xdrop:fuzzywuzzy:1.4.0")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.6.4")
    }
}

tasks.named<Delete>("clean") {
    delete(rootProject.buildDir)
}
