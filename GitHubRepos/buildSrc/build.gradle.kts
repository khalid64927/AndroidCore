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

dependencies {
    implementation("com.google.devtools.ksp:symbol-processing-api:1.6.10-1.0.4")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
    implementation("com.android.tools.build:gradle:7.3.0-alpha07")
    implementation("org.jetbrains.kotlin:kotlin-allopen:1.6.10")
    implementation("androidx.navigation:navigation-safe-args-gradle-plugin:2.4.1")
    implementation ("com.diffplug.spotless:spotless-plugin-gradle:6.3.0")
    implementation ("org.owasp:dependency-check-gradle:7.0.0")
}


gradlePlugin {
    plugins {
        create("KhalidAndroidPlugin") {
            id = "com.khalid.hamid.KhalidAndroidPlugin"
            implementationClass = "com.dependencies.KhalidAndroidPlugin"
        }
    }
}

