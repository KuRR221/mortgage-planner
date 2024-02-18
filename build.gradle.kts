plugins {
    java
}

version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
}

tasks.named<Jar>("jar") {
    manifest {
        attributes["Main-Class"] = "org.example.calculator.mortgageCalculator"
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}