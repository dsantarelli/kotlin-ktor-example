group = "com.ktor.example"
version = "1.0.0"

val ktorVersion = "1.5.2"
val jupiterVersion = "5.6.0"
val mapStructVersion = "1.4.2.Final"
val jacksonKotlinVersion = "2.12.2"
val logbackClassicVersion = "1.2.3"
val hikariVersion = "4.0.3"
val h2DatabaseVersion = "1.4.200"
val exposedVersion = "0.12.1"
val koinVersion = "2.2.2"

plugins {
    kotlin("jvm") version "1.4.10"
    kotlin("kapt") version "1.4.31"
    application
    jacoco
}

repositories {
    jcenter()
    mavenCentral()
    maven { url = uri("https://dl.bintray.com/kotlin/kotlinx") }
    maven { url = uri("https://dl.bintray.com/kotlin/ktor") }
}

dependencies {

    testImplementation("org.junit.jupiter:junit-jupiter-api:$jupiterVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$jupiterVersion")
    testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
    testImplementation("io.ktor:ktor-server-test-host:$ktorVersion")

    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-locations:$ktorVersion")
    implementation("io.ktor:ktor-jackson:$ktorVersion")
    implementation("org.mapstruct:mapstruct:$mapStructVersion")
    kapt("org.mapstruct:mapstruct-processor:$mapStructVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonKotlinVersion")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonKotlinVersion")
    implementation("ch.qos.logback:logback-classic:$logbackClassicVersion")
    implementation("com.zaxxer:HikariCP:$hikariVersion")
    implementation("com.h2database:h2:$h2DatabaseVersion")
    implementation("org.jetbrains.exposed:exposed:$exposedVersion")
    implementation("org.koin:koin-core:$koinVersion")
}

jacoco {
    toolVersion = "0.8.6"
}

tasks.compileKotlin {
    kotlinOptions.jvmTarget = "11"
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        html.isEnabled = true
        xml.isEnabled = true
        csv.isEnabled = false
    }
}

application {
    mainClassName = "com.ktor.example.App"
}
