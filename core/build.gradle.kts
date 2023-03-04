import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

description = "core"

val apacheCommonsVersion by properties

plugins {
    id("org.springframework.boot") version "2.7.5"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    id("maven-publish")
    kotlin("plugin.spring") version "1.6.21"
    kotlin("jvm") version "1.6.21"
}

group = "com.xyz"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    api("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.apache.commons:commons-lang3:$apacheCommonsVersion")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.bootJar {
    enabled = false
}

publishing {
    publications {

        create<MavenPublication>("maven-publication") {
            groupId = "com.cheypnow.web-request-tracing"
            artifactId = "core"
            version = "1.0"

            from(components["java"])
        }
    }
}