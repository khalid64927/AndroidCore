plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    google()
    maven{
        url = uri("https://dl.bintray.com/kotlin/kotlin-eap")
    }
}

object GlobalVersions {
    const val DAGGER = "2.41"
    const val KOTLIN = "1.6.10"
}

dependencies {
    implementation("com.google.devtools.ksp:symbol-processing-api:1.6.10-1.0.4")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${GlobalVersions.KOTLIN}")
    implementation("com.android.tools.build:gradle:7.3.0-alpha07")
    implementation("org.jetbrains.kotlin:kotlin-allopen:${GlobalVersions.KOTLIN}")
    implementation("androidx.navigation:navigation-safe-args-gradle-plugin:2.4.1")
    implementation ("com.diffplug.spotless:spotless-plugin-gradle:6.3.0")
    implementation ("org.owasp:dependency-check-gradle:7.0.1")
    implementation ("com.google.dagger:hilt-android-gradle-plugin:${GlobalVersions.DAGGER}")
}


gradlePlugin {
    plugins {
        create("KhalidAndroidPlugin") {
            id = "com.khalid.hamid.KhalidAndroidPlugin"
            implementationClass = "com.dependencies.KhalidAndroidPlugin"
        }
    }
}

