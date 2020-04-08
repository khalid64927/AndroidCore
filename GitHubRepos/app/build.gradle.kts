import com.dependencies.Dependencies

plugins {
    id("com.diffplug.gradle.spotless") version ("3.25.0")
    id("com.khalid.hamid.KhalidAndroidPlugin")

}
KPlugin {
    isLibraryModule = false
    minSDK = 19
    compileSDK = "android-R"
    targetSDK = "R"
    versionCode = 10
    versionName = "1.1"
    testRunner = "androidx.test.runner.AndroidJUnitRunner"
    lintBaseLineFilePath = "com.khalid.hamid.githubrepos.utilities.AppTestRunner"
    checkstylePath = "$rootDir/quality/checkstyle.xml"
}



spotless {
    kotlin {
        target ("**/*.kt")
        ktlint("0.35.0").userData(mapOf("disabled_rules" to "no-wildcard-imports"))
            licenseHeaderFile(project.rootProject.file("scripts/copyright.kt"))
    }
}

allOpen.annotation("com.khalid.hamid.githubrepos.testing.OpenClass")
android {
    defaultConfig {
        applicationId = "com.khalid.hamid.githubrepos"
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    flavorDimensions("default")
    productFlavors {
        create("mock") {
            applicationId = "com.khalid.hamid.githubrepos"
        }
        create("prod") {
            applicationId = "com.khalid.hamid.githubrepos"
        }
    }
}
dependencies {
    implementation(Dependencies.KOTLIN)
    implementation(Dependencies.V7)
    implementation(Dependencies.CONSTRAINT_LAYOUT)
    implementation(Dependencies.TIMBER)
    implementation(Dependencies.MULTIDEX)
    implementation(Dependencies.CRASH)

    implementation(Dependencies.DAGGER_RUNTIME)
    implementation(Dependencies.DAGGER_ANDROID)
    implementation(Dependencies.DAGGER_ANDROID_SUPPORT)
    kapt(Dependencies.DAGGER_ANDROID_PROCESSOR)
    kapt(Dependencies.DAGGER_COMPILER)
    // Lifecycle component
    implementation(Dependencies.LC_EXTENSION)
    implementation(Dependencies.LC_JAVA8)
    implementation(Dependencies.LC_RUNTIME)
    kapt(Dependencies.LC_COMPILER)
    kapt(Dependencies.LC_VM_KTX)
    // Rx Java
    implementation(Dependencies.RX_ANDROID)
    // Room component
    implementation(Dependencies.ROOM_RUNTIME)
    implementation(Dependencies.ROOM_TESTING)
    kapt(Dependencies.ROOM_COMPILER)
    implementation(Dependencies.ROOM_KTX)

    implementation(Dependencies.RETROFIT_ADAPTER)
    implementation(Dependencies.RETROFIT_GSON_CONVERTER)
    implementation(Dependencies.RETROFIT_RUNTIME)

    implementation(Dependencies.OKHTTP)
    implementation(Dependencies.OKHTTP_INTERCEPTOR)

    implementation(Dependencies.ESP_IDL)

    implementation(Dependencies.FRAGMENT)
    implementation(Dependencies.FRAGMENTKTX)
    implementation(Dependencies.ACTIVITY_KTX)
    implementation(Dependencies.FRAGMENT_TESTING)
    implementation(Dependencies.TEST_CORE)
    // UI
    implementation(Dependencies.SHIMMER)
    implementation(Dependencies.SWIPEX)

    implementation(Dependencies.GOOGLE_MATERIAL)
    implementation(Dependencies.ANNOTATIONS)
    implementation(Dependencies.CARD_VIEW)
    // Image library
    implementation(Dependencies.GLIDE_RUNTIME)
    kapt(Dependencies.GLIDE_COMPILER)
    // Nav
    implementation(Dependencies.NAV_RUNTIME_FRAGMENT_KTX)
}


