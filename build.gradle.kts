plugins {
    // Apply the java plugin to add support for Java
    java
    // Apply the application plugin to add support for building a CLI application.
    application
    id("io.freefair.lombok") version "6.0.0-m2"
}

repositories {
    // Use jcenter for resolving dependencies.
    // You can declare any Maven/Ivy/file repository here.
    jcenter()
}

dependencies {
    // This dependency is used by the application.
    implementation("com.google.guava:guava:28.1-jre")
    implementation("org.projectlombok:lombok:1.18.20")
    implementation("org.seleniumhq.selenium:selenium-java:3.141.59")

    // Use JUnit test framework
    testImplementation("junit:junit:4.12")
}

application {
    // Define the main class for the application.
    mainClassName = "docto.scraper.App"
}
