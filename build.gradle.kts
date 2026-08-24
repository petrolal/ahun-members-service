plugins {
    java
    `maven-publish`
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
}

group = "com.petrolal.ahun.members"
val rawVersion = System.getenv("VERSION") ?: (project.findProperty("version") as? String) ?: "0.0.1-SNAPSHOT"
version = rawVersion.removePrefix("v")
description = "ahun-members-service"

springBoot {
    mainClass.set("com.petrolal.ahun.members.AhunMembersServiceApplication")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/petrolal/ahun-members-service")
            credentials {
                username = System.getenv("GITHUB_ACTOR") ?: ""
                password = System.getenv("GITHUB_TOKEN") ?: ""
            }
        }
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenLocal()
    mavenCentral()
    maven {
        url = uri("https://maven.pkg.github.com/petrolal/spring-commons-web")
        credentials {
            username = System.getenv("GITHUB_ACTOR") ?: "petrolal"
            password = System.getenv("GITHUB_TOKEN") ?: System.getenv("GH_PAT")
        }
    }
    maven {
        url = uri("https://maven.pkg.github.com/petrolal/commons-telegram")
        credentials {
            username = System.getenv("GITHUB_ACTOR") ?: "petrolal"
            password = System.getenv("GITHUB_TOKEN") ?: System.getenv("GH_PAT")
        }
    }
}

dependencies {
    implementation(libs.telegrambots.spring.boot.starter)
    implementation(libs.petrolal.commons.web)
}

tasks.withType<Test> {
    useJUnitPlatform()
}
