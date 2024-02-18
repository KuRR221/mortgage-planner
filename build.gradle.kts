plugins {
    java
}

version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
}

tasks.named<Jar>("jar") {
    manifest {
        attributes["Main-Class"] = "mortgageCalculator"
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}