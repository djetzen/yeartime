import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.4.3"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	id("org.flywaydb.flyway") version "7.5.4"
	id("org.jetbrains.kotlin.plugin.jpa") version "1.4.21"
	jacoco
	kotlin("plugin.serialization") version "1.4.21"
	kotlin("plugin.spring") version "1.4.21"
	kotlin("jvm") version "1.4.21"

}

apply(plugin = "kotlin-jpa")
group = "de.djetzen"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.projectlombok:lombok:1.18.18")
	implementation("org.flywaydb:flyway-core")
	implementation("org.postgresql:postgresql:42.2.19")
	testImplementation("com.h2database:h2")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("com.tngtech.archunit:archunit-junit5:0.17.0")
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


tasks.jacocoTestReport {
	reports {
		xml.isEnabled = true
		csv.isEnabled = false
	}
}
