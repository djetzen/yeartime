import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.6.2"
	id("io.spring.dependency-management") version "1.1.0"
	id("org.flywaydb.flyway") version "9.8.3"
	id("org.jetbrains.kotlin.plugin.jpa") version "1.7.22"
	jacoco
	kotlin("plugin.serialization") version "1.7.22"
	kotlin("plugin.spring") version "1.7.22"
	kotlin("jvm") version "1.7.22"

}

apply(plugin = "kotlin-jpa")
group = "de.djetzen"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.projectlombok:lombok:1.18.24")
	implementation("org.flywaydb:flyway-core")
	implementation("org.postgresql:postgresql:42.5.1")
	testImplementation("com.h2database:h2")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("com.tngtech.archunit:archunit-junit5:1.0.1")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}


tasks.jacocoTestReport {
	reports {
		xml.isEnabled = true
		csv.isEnabled = false
	}
}
