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
    implementation(dependencyNotation = "com.google.devtools.ksp:symbol-processing-api:1.6.10-1.0.4")
    implementation(dependencyNotation = "org.jetbrains.kotlin:kotlin-gradle-plugin:${GlobalVersions.KOTLIN}")
    implementation(dependencyNotation = "com.android.tools.build:gradle:7.4.0-alpha05")
    implementation(dependencyNotation = "org.jetbrains.kotlin:kotlin-allopen:${GlobalVersions.KOTLIN}")
    implementation(dependencyNotation = "androidx.navigation:navigation-safe-args-gradle-plugin:2.4.2")
    implementation (dependencyNotation = "com.diffplug.spotless:spotless-plugin-gradle:6.3.0")
    implementation (dependencyNotation = "org.owasp:dependency-check-gradle:7.0.1")
    implementation (dependencyNotation = "com.google.dagger:hilt-android-gradle-plugin:${GlobalVersions.DAGGER}")
}


gradlePlugin {
    plugins {
        create("KhalidAndroidPlugin") {
            id = "com.khalid.hamid.KhalidAndroidPlugin"
            implementationClass = "com.dependencies.KhalidAndroidPlugin"
        }
    }
}



