plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
}

repositories {
    google()
    jcenter()
    maven{
        url = uri("https://dl.bintray.com/kotlin/kotlin-eap")
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.60-eap-25")
    implementation("com.android.tools.build:gradle:4.0.0-beta03")
    implementation("org.jetbrains.kotlin:kotlin-allopen:1.3.60-eap-25")
    implementation("android.arch.navigation:navigation-safe-args-gradle-plugin:1.0.0")
    implementation ("com.diffplug.spotless:spotless-plugin-gradle:3.27.0")

}


gradlePlugin {
    plugins {
        create("KhalidAndroidPlugin") {
            id = "com.khalid.hamid.KhalidAndroidPlugin"
            implementationClass = "com.dependencies.KhalidAndroidPlugin"
        }
    }
}
