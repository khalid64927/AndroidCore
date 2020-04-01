import com.dependencies.Dependencies
import com.dependencies.Versions

plugins {
    id("com.khalid.hamid.KhalidAndroidPlugin")
}

//spotless {
//    kotlin {
//        target ("**/*.kt")
//        ktlint("0.35.0").userData(mapOf("disabled_rules" to "no-wildcard-imports"))
//        licenseHeaderFile(project.rootProject.file("scripts/copyright.kt"))
//    }
//}
allOpen.annotation("com.khalid.hamid.githubrepos.testing.OpenClass")

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Dependencies.KOTLIN)
    implementation(Dependencies.V7)
    implementation(Dependencies.CONSTRAINT_LAYOUT)
    implementation(Dependencies.TIMBER)
    implementation(Dependencies.DAGGER_RUNTIME)
    implementation(Dependencies.DAGGER_ANDROID)
    implementation(Dependencies.DAGGER_ANDROID_SUPPORT)
    // Lifecycle component
    implementation(Dependencies.LC_EXTENSION)
    implementation(Dependencies.LC_JAVA8)
    implementation(Dependencies.LC_RUNTIME)
    // Rx Java
    implementation(Dependencies.RX_ANDROID)
    // Room component
    implementation(Dependencies.ROOM_RUNTIME)
    implementation(Dependencies.ROOM_TESTING)
    implementation(Dependencies.ROOM_KTX)
    implementation(Dependencies.RETROFIT_ADAPTER)
    implementation(Dependencies.RETROFIT_GSON_CONVERTER)
    implementation(Dependencies.RETROFIT_RUNTIME)
    implementation(Dependencies.OKHTTP)
    implementation(Dependencies.OKHTTP_INTERCEPTOR)
    implementation(Dependencies.ESP_IDL)
    implementation(Dependencies.FRAGMENT)
    implementation(Dependencies.FRAGMENT_TESTING)
    implementation(Dependencies.TEST_CORE)
    // UI
    implementation(Dependencies.SHIMMER)
    implementation(Dependencies.GOOGLE_MATERIAL)
    implementation(Dependencies.ANNOTATIONS)
    implementation(Dependencies.CARD_VIEW)
    // Image library
    implementation(Dependencies.GLIDE_RUNTIME)
    // Nav
    implementation(Dependencies.NAV_RUNTIME_FRAGMENT_KTX)

    kapt(Dependencies.ROOM_COMPILER)
    kapt(Dependencies.LC_COMPILER)
    kapt(Dependencies.DAGGER_ANDROID_PROCESSOR)
    kapt(Dependencies.DAGGER_COMPILER)
    kapt(Dependencies.GLIDE_COMPILER)
}

