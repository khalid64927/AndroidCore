import com.dependencies.Dependencies
import com.dependencies.pln

plugins {
    id("com.khalid.hamid.KhalidAndroidPlugin")
    // TODO: add ksp id("com.google.devtools.ksp") version ("1.6.10-1.0.4")
}
pln("after plugin")

KPlugin {
    pln("KPlugin Ext app...")
    isLibraryModule = false
    minSDK = 19
    compileSDK = "33"
    targetSDK = "33"
    versionCode = 10
    versionName = "1.1"
    testRunner = "com.khalid.hamid.githubrepos.utilities.AppTestRunner"
    lintBaseLineFilePath = "$rootDir/quality/lint-baseline.xml"
    checkstylePath = "$rootDir/quality/checkstyle.xml"
}

allOpen.annotation("com.khalid.hamid.githubrepos.testing.OpenClass")
android {
    pln("android block...")
    namespace = "com.khalid.hamid.githubrepos"
    defaultConfig {
        applicationId = "com.khalid.hamid.githubrepos"
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    flavorDimensions.add("default")
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
    implementation(Dependencies.HILT_ANDROID)
    kapt(Dependencies.HILT_ANDROID_COMPILER)

    implementation(Dependencies.KOTLIN)
    implementation(Dependencies.V7)
    implementation(Dependencies.CONSTRAINT_LAYOUT)
    implementation(Dependencies.TIMBER)
    implementation(Dependencies.MULTIDEX)
    implementation(Dependencies.CRASH)
    implementation(Dependencies.CR_CORE)
    implementation(Dependencies.CR_ANDROID)

    implementation(Dependencies.DAGGER_RUNTIME)
    api(Dependencies.DAGGER_ANDROID)
    implementation(Dependencies.DAGGER_ANDROID_SUPPORT)
    kapt(Dependencies.DAGGER_ANDROID_PROCESSOR)
    kapt(Dependencies.DAGGER_COMPILER)

    // Epoxy
    implementation(Dependencies.EPOXY)
    kapt(Dependencies.EPOXY_RUNTIME)
    implementation(Dependencies.EPOXY_DATABINDING)

    // Photo View
    implementation(Dependencies.CHRISBANES_PHOTO)

    // Lifecycle component
    implementation(Dependencies.LC_EXTENSION)
    implementation(Dependencies.LC_JAVA8)
    implementation("android.arch.lifecycle:common-java8:1.1.1")
    implementation(Dependencies.LC_RUNTIME)
    implementation(Dependencies.LD_KTX)
    kapt(Dependencies.LC_COMPILER)
    kapt(Dependencies.LC_VM_KTX)
    // Room component
    implementation(Dependencies.ROOM_RUNTIME)
    implementation(Dependencies.ROOM_TESTING)
    // TODO:
    kapt(Dependencies.ROOM_COMPILER)
    implementation(Dependencies.ROOM_KTX)

    implementation(Dependencies.RETROFIT_ADAPTER)
    implementation(Dependencies.RETROFIT_GSON_CONVERTER)
    implementation(Dependencies.RETROFIT_RUNTIME)

    implementation(Dependencies.OKHTTP)
    implementation(Dependencies.OKHTTP_INTERCEPTOR)

    implementation(Dependencies.ESP_IDL)

    implementation(Dependencies.FRAGMENTKTX)
    implementation(Dependencies.ACTIVITY_KTX)
    implementation(Dependencies.FRAGMENT_TESTING)
    implementation(Dependencies.TEST_CORE)
    // UI
    implementation(Dependencies.SHIMMER)
    implementation(Dependencies.SWIPEX)
    implementation(Dependencies.RECYCLER_VIEW)

    implementation(Dependencies.GOOGLE_MATERIAL){
        exclude(group = "androidx.recyclerview")
    }
    implementation(Dependencies.ANNOTATIONS)
    implementation(Dependencies.CARD_VIEW)
    // Image library
    implementation(Dependencies.GLIDE_RUNTIME)
    kapt(Dependencies.GLIDE_COMPILER)
    // Nav
    implementation(Dependencies.NAV_RUNTIME_FRAGMENT_KTX)
    debugImplementation(Dependencies.CHUCKER)
}

pln("script end")

