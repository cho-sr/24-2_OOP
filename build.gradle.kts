plugins {
    kotlin("jvm") version "2.0.20"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
}

tasks.test {
    useJUnitPlatform()
}
// build.gradle.kts

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "17" // 지원되는 최신 JVM 버전으로 변경
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17)) // Java 17로 변경
    }
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.2")
}
dependencies {
    implementation("org.json:json:20230618")
}
dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
}
dependencies {
    implementation("com.opencsv:opencsv:5.6")  // OpenCSV 의존성 추가
}
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}





