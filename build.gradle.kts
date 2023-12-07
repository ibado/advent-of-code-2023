plugins {
    kotlin("jvm") version "1.9.20"
}

dependencies {
    implementation("org.junit.jupiter:junit-jupiter:5.9.2")
}

sourceSets {
    main {
        kotlin.srcDir("src")
    }
    test {
        kotlin.srcDir("test")
    }
}

repositories {
    mavenCentral()
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter()
        }
    }
}

tasks {
    wrapper {
        gradleVersion = "8.5"
    }
}
