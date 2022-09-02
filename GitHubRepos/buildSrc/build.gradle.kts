plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
    google()
    maven{
        url = uri("https://dl.bintray.com/kotlin/kotlin-eap")
    }
}

object GlobalVersions {
    const val DAGGER = "2.43"
    const val KOTLIN = System.getProperty("kotlin")
}



dependencies {
    // TODO: replace kapt with KSP
    implementation("com.google.devtools.ksp:symbol-processing-api:1.6.10-1.0.4")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${GlobalVersions.KOTLIN}")
    implementation("com.android.tools.build:gradle:7.4.0-alpha10")
    implementation("org.jetbrains.kotlin:kotlin-allopen:${GlobalVersions.KOTLIN}")
    implementation("androidx.navigation:navigation-safe-args-gradle-plugin:2.5.0-rc02")
    implementation ("com.diffplug.spotless:spotless-plugin-gradle:6.8.0")
    implementation ("org.owasp:dependency-check-gradle:7.1.1")
    implementation ("com.github.ben-manes:gradle-versions-plugin:0.42.0")
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

