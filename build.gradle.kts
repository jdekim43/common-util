import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform") version "1.4.32"
    id("maven-publish")
}

val artifactName = "common-util"
val artifactGroup = "kr.jadekim"
val artifactVersion = "1.1.13"
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
            artifactId = "$artifactName-jvm"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                val kotlinxVersion: String by project

                compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxVersion")
            }
        }
        val jvmMain by getting {
            dependencies {
                val kotlinxVersion: String by project

                compileOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:$kotlinxVersion")
            }

            tasks.withType<KotlinCompile> {
                val jvmTarget: String by project

                kotlinOptions.jvmTarget = jvmTarget
            }
        }

        all {
            languageSettings.useExperimentalAnnotation("kotlin.contracts.ExperimentalContracts")
        }
    }

    publishing {
        repositories {
            maven {
                val jfrogUsername: String by project
                val jfrogPassword: String by project

                setUrl("https://jadekim.jfrog.io/artifactory/maven/")

                credentials {
                    username = jfrogUsername
                    password = jfrogPassword
                }
            }
        }
    }
}