plugins {
    java
    id("io.freefair.lombok") version "5.1.1"
}

group = "com.ceejay"
version = "0.0.1"

val java6Home: String by project

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.commons", "commons-lang3", "3.5")
    implementation("commons-collections", "commons-collections", "3.2.2")
    implementation("org.jetbrains", "annotations-java5", "20.0.0")
    implementation("junit", "junit", "4.12")

    testImplementation("org.assertj", "assertj-core", "1.7.1")
}


tasks {
    withType<JavaCompile> {
        options.isFork = true
        options.forkOptions.javaHome = file(java6Home)
        sourceCompatibility = "1.6"
        targetCompatibility = sourceCompatibility
    }

    generateLombokConfig {
        enabled = false
    }

    test {
        testLogging.showExceptions = true
    }
}

lombok {
    version.set("1.18.2")
}