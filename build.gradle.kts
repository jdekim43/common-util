import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.util.Date

plugins {
    kotlin("multiplatform") version "1.4.10"
    `maven-publish`
    id("com.jfrog.bintray") version "1.8.4"
}

val artifactName = "common-util"
val artifactGroup = "kr.jadekim"
val artifactVersion = "1.1.7"
group = artifactGroup
version = artifactVersion

repositories {
    jcenter()
    mavenCentral()
}

kotlin {
    metadata {
        mavenPublication {
            artifactId = "$artifactName-common"
        }
    }
    jvm {
        mavenPublication {
            artifactId = artifactName
        }
    }

    sourceSets {
        val commonMain by getting {}
        val jvmMain by getting {
            tasks.withType<KotlinCompile> {
                val jvmTarget: String by project

                kotlinOptions.jvmTarget = jvmTarget
            }
        }
    }
}

bintray {
    user = System.getenv("BINTRAY_USER")
    key = System.getenv("BINTRAY_KEY")

    publish = true

    setPublications("jvm", "metadata")

    pkg.apply {
        repo = "maven"
        name = "common-util"
        setLicenses("MIT")
        setLabels("kotlin")
        vcsUrl = "https://github.com/jdekim43/common-util.git"
        version.apply {
            name = artifactVersion
            released = Date().toString()
        }
    }
}