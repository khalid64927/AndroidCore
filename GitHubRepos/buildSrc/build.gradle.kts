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
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.72")
    implementation("com.android.tools.build:gradle:4.1.0")
    implementation("org.jetbrains.kotlin:kotlin-allopen:1.3.72")
    implementation("android.arch.navigation:navigation-safe-args-gradle-plugin:1.0.0")
    implementation ("com.diffplug.spotless:spotless-plugin-gradle:3.27.0")
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

