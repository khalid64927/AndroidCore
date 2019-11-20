import com.dependencies.Dependencies
import com.dependencies.Versions

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("org.jetbrains.kotlin.plugin.allopen")
    id("androidx.navigation.safeargs")
    id("jacoco")
}
allOpen.annotation("com.khalid.hamid.githubrepos.testing.OpenClass")
android {
    compileSdkVersion(Versions.MAX_SDK)
    buildToolsVersion(Versions.BUILD_TOOLS)
    defaultConfig {
        applicationId = "com.khalid.hamid.githubrepos"
        minSdkVersion(Versions.MIN_SDK)
        targetSdkVersion(Versions.MAX_SDK)
        dataBinding.isEnabled = true
        dataBinding.isEnabledForTests = true
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "com.khalid.hamid.githubrepos.utilities.AppTestRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    packagingOptions {
        exclude("LICENSE.txt")
        exclude("META-INF/rxjava.properties")
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/LICENSE")
        exclude("META-INF/LICENSE.txt")
        exclude("META-INF/license.txt")
        exclude("META-INF/NOTICE")
        exclude("META-INF/NOTICE.txt")
        exclude("META-INF/notice.txt")
        exclude("META-INF/ASL2.0")
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
    testOptions.unitTests.isReturnDefaultValues = true
    testOptions.unitTests.isIncludeAndroidResources = true
    // compile bytecode to java 8 (default is java 6)
    compileOptions {
        setSourceCompatibility(JavaVersion.VERSION_1_8)
        setTargetCompatibility(JavaVersion.VERSION_1_8)
    }
    lintOptions {

    }
}
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
    // JUNIT
    testImplementation(Dependencies.JUNIT)
    testImplementation(Dependencies.JUNITX)
    testImplementation(Dependencies.MOCKWEBSERVER)
    testImplementation(Dependencies.ARCH_CORE_TESTING)
    testImplementation(Dependencies.ESPRESSO_CORE)
    testImplementation(Dependencies.ESP_CONTRIBUTE)
    testImplementation(Dependencies.ESP_INTENTS)
    testImplementation(Dependencies.TEST_KTX_CORE)
    testImplementation(Dependencies.TEST_KTX_JUNIT)
    testImplementation(Dependencies.TEST_RULES)
    testImplementation(Dependencies.MOKITO_CORE)
    testImplementation(Dependencies.MOKITO_ALL)
    testImplementation(Dependencies.MOKITO_INLINE)
    //CR
    testImplementation(Dependencies.CR_TEST)
    androidTestImplementation(Dependencies.NAV_TESTING_KTX)
    androidTestImplementation(Dependencies.RECYCLER_VIEW)
    androidTestImplementation(Dependencies.CARD_VIEW)
    androidTestImplementation(Dependencies.DESIGN)
    androidTestImplementation(Dependencies.JUNITX)
    androidTestImplementation(Dependencies.RECYCLER_VIEW)
    androidTestImplementation(Dependencies.GOOGLE_MATERIAL)
    // JUNIT
    androidTestImplementation(Dependencies.TEST_RULES)
    androidTestImplementation(Dependencies.ARCH_CORE_TESTING)
    androidTestImplementation(Dependencies.MOKITO_CORE)
    androidTestImplementation(Dependencies.ESPRESSO_CORE, {
        exclude(group = "com.android.support", module = "support-annotations")
        exclude(group = "com.google.code.findbugs", module = "jsr305")
    })
    androidTestImplementation(Dependencies.MOKITO_CORE,
        { exclude(group = "net.bytebuddy") })
}


