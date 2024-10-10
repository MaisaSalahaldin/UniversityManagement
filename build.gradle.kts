plugins {
    kotlin("jvm") version "1.8.10" // Adjust to the appropriate Kotlin version
    application // Optional: if you plan to run your application
}

repositories {
    mavenCentral() // To fetch dependencies from Maven Central
}

dependencies {
    implementation("mysql:mysql-connector-java:8.0.30")
    implementation("org.apache.poi:poi:5.2.2")
    implementation("org.apache.poi:poi-ooxml:5.2.2")
}

// Optional: Define the main class for the application plugin
application {
    mainClass.set("your.package.name.JasperReportTest") // Replace with your main class path
}
