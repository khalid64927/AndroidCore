import com.dependencies.Dependencies

plugins {
    id("com.khalid.hamid.KhalidAndroidPlugin")
}
kapt {
    correctErrorTypes = true
    arguments {
        arg("-Xjvm-default", "compatibility")
    }
}

KPlugin {
    println("Library ... ")
    isLibraryModule = false
    minSDK = 19
    compileSDK = "32"
    targetSDK = "32"
    versionCode = 10
    versionName = "1.1"
    testRunner = "androidx.test.runner.AndroidJUnitRunner"
    lintBaseLineFilePath = "$rootDir/quality/lint-baseline.xml"
    checkstylePath = "$rootDir/quality/checkstyle.xml"
}


allOpen.annotation("com.khalid.hamid.githubrepos.testing.OpenClass")

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Dependencies.KOTLIN)
    implementation(Dependencies.V7)
    implementation(Dependencies.CONSTRAINT_LAYOUT)
    implementation(Dependencies.TIMBER)
    //implementation(Dependencies.DAGGER_RUNTIME)
    api(Dependencies.DAGGER_ANDROID)
    //implementation(Dependencies.DAGGER_ANDROID_SUPPORT)
    implementation(Dependencies.MULTIDEX)
    // Lifecycle component
    implementation(Dependencies.LC_EXTENSION)
    implementation(Dependencies.LC_JAVA8)
    implementation(Dependencies.LC_RUNTIME)
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
    implementation(Dependencies.FRAGMENT_TESTING)
    implementation(Dependencies.TEST_CORE)
    // UI
    implementation(Dependencies.SHIMMER)
    implementation(Dependencies.GOOGLE_MATERIAL){
        exclude(group = "androidx.recyclerview")
    }
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


