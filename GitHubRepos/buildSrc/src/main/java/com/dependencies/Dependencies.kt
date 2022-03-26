package com.dependencies

object Versions {
    const val VERSION_NAME = "1.0.1"
    const val VERSION_CODE = 1011
    const val RETROFIT = "2.9.0"
    const val OKHTTP_INTERCEPTOR = "4.9.3"
    const val RXJAVA = "3.0.0"
    const val MIN_SDK = 19
    const val BUILD_TOOLS = "29.0.1"
    const val MAX_SDK = 2
    const val LIFECYCLE = "2.5.0-alpha03"
    const val ROOM = "2.5.0-alpha01"
    const val FRAGMENT = "1.5.0-alpha03"
    const val NAV = "2.4.1" // NAV_SAFE_ARG
    const val NAV_TESTING = "1.3.0-alpha03"
    const val JUNITX = "1.1.4-alpha04"
    const val TESTX = "1.4.1-alpha04"
    const val KOTLIN = "1.6.10"
    const val SPOTLESS = "6.3.0"
    const val NAV_SAFE_ARG = "2.4.1"
    const val DAGGER = "2.41"
}

object Dependencies {
    val submodules = listOf("submodule1", "submodule2", "submodule3")
    const val ANNOTATIONS = "androidx.annotation:annotation:1.1.0"
    const val MULTIDEX = "androidx.multidex:multidex:2.0.1"
    const val MULTIDEXTEST = "androidx.multidex:multidex-instrumentation:2.0.0"
    const val V7 = "androidx.appcompat:appcompat:1.6.0-alpha01"
    const val SWIPEX = "androidx.swiperefreshlayout:swiperefreshlayout:1.2.0-alpha01"
    const val GOOGLE_MATERIAL = "com.google.android.material:material:1.6.0-alpha03"
    const val RECYCLER_VIEW = "androidx.recyclerview:recyclerview:1.3.0-alpha01"
    const val CARD_VIEW = "androidx.cardview:cardview:1.0.0"
    const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:2.1.3"
    // Add below for Dagger
    const val DAGGER_RUNTIME = "com.google.dagger:dagger:${Versions.DAGGER}"
    const val DAGGER_ANDROID = "com.google.dagger:dagger-android:${Versions.DAGGER}"
    const val DAGGER_ANDROID_SUPPORT = "com.google.dagger:dagger-android-support:${Versions.DAGGER}"
    const val DAGGER_COMPILER = "com.google.dagger:dagger-compiler:${Versions.DAGGER}"
    // HILT
    const val HILT_ANDROID = "com.google.dagger:hilt-android:${Versions.DAGGER}"
    const val HILT_COMPILER = "com.google.dagger:hilt-compiler:${Versions.DAGGER}"
    const val HILT_ANDROID_COMPILER = "com.google.dagger:hilt-android-compiler:${Versions.DAGGER}"
    // Testing
    const val HILT_ANDROID_TESTING = "com.google.dagger:hilt-android-testing${Versions.DAGGER}"

    const val CRASHLYTICS                = "com.crashlytics.sdk.android:crashlytics:2.10.1"
    // kapt here
    const val DAGGER_ANDROID_PROCESSOR   = "com.google.dagger:dagger-android-processor:${Versions.DAGGER}"
    // OKHTTP
    const val OKHTTP_INTERCEPTOR         = "com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP_INTERCEPTOR}"
    const val OKHTTP                     = "com.squareup.okhttp3:okhttp:${Versions.OKHTTP_INTERCEPTOR}"
    // Add below for Dagger
    const val RETROFIT_RUNTIME           = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
    const val RETROFIT_GSON_CONVERTER    = "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT}"
    const val RETROFIT_ADAPTER           = "com.squareup.retrofit2:adapter-rxjava3:${Versions.RETROFIT}"

    const val TIMBER                     = "com.jakewharton.timber:timber:4.6.0"
    // Not used in this project
    const val RX_ANDROID                 = "io.reactivex.rxjava3:rxandroid:${Versions.RXJAVA}"
    const val GLIDE_RUNTIME              = "com.github.bumptech.glide:glide:4.13.1"
    const val GLIDE_COMPILER             = "com.github.bumptech.glide:compiler:4.13.1"
    const val JUNIT                      = "junit:junit:4.13"
    const val ESP_IDL                    = "androidx.test.espresso:espresso-idling-resource:3.5.0-alpha04"
    const val ESP_CONTRIBUTE             = "androidx.test.espresso:espresso-contrib:3.5.0-alpha04"
    const val ESP_INTENTS                = "androidx.test.espresso:espresso-intents:3.5.0-alpha04"
    const val ESPRESSO_CORE              = "androidx.test.espresso:espresso-core:3.5.0-alpha04"
    const val MOKITO_CORE                = "org.mockito:mockito-core:4.3.1"
    const val MOKITO_ALL                 = "org.mockito:mockito-all:2.0.2-beta"
    const val MOKITO_INLINE              = "org.mockito:mockito-inline:4.3.1"
    const val MOCKWEBSERVER              = "com.squareup.okhttp3:mockwebserver:${Versions.OKHTTP_INTERCEPTOR}"
    const val KOTLIN                     = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.KOTLIN}"
    const val SHIMMER                    = "com.facebook.shimmer:shimmer:0.5.0"
    const val LC_RUNTIME                 = "androidx.lifecycle:lifecycle-runtime:${Versions.LIFECYCLE}"
    const val LC_COMPILER                = "androidx.lifecycle:lifecycle-compiler:${Versions.LIFECYCLE}"
    const val LC_JAVA8                   = "androidx.lifecycle:lifecycle-common-java8:${Versions.LIFECYCLE}"
    const val LC_EXTENSION               = "androidx.lifecycle:lifecycle-extensions:2.2.0-rc03"
    const val LD_KTX                     = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.LIFECYCLE}"
    const val LC_VM_KTX                  = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.LIFECYCLE}"
    const val ROOM_RUNTIME               = "androidx.room:room-runtime:${Versions.ROOM}"
    const val ROOM_COMPILER              = "androidx.room:room-compiler:${Versions.ROOM}"
    const val ROOM_KTX                   = "androidx.room:room-ktx:${Versions.ROOM}"
    const val ROOM_TESTING               = "androidx.room:room-testing:${Versions.ROOM}"
    const val ARCH_CORE_TESTING          = "androidx.arch.core:core-testing:2.1.0"
    const val NAV_RUNTIME_FRAGMENT_KTX   = "androidx.navigation:navigation-fragment-ktx:${Versions.NAV}"
    const val TEST_CORE                  = "androidx.test:core:${Versions.TESTX}"
    const val FRAGMENTKTX                = "androidx.fragment:fragment-ktx:${Versions.FRAGMENT}"
    const val ACTIVITY_KTX               = "androidx.activity:activity-ktx:1.5.0-alpha03"
    const val FRAGMENT_TESTING           = "androidx.fragment:fragment-testing:${Versions.FRAGMENT}"

    // testing
    const val TEST_KTX_CORE              = "androidx.test:core-ktx:${Versions.TESTX}"
    const val TEST_KTX_JUNIT             = "androidx.test.ext:junit-ktx:${Versions.JUNITX}"
    const val JUNIT_EXT                  = "androidx.test.ext:junit:${Versions.JUNITX}"
    const val TEST_RULES                 = "androidx.test:rules:${Versions.TESTX}"
    const val CR_TEST                    = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0"
    const val CR_CORE                    = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0"
    const val CR_ANDROID                 = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0"
    const val CR_TEST_DEBUG              = "org.jetbrains.kotlinx:kotlinx-coroutines-debug:1.6.0"
    const val CRASH                      = "com.google.firebase:firebase-crashlytics:17.0.0-beta03"
    const val KLUENT                     = "org.amshove.kluent:kluent:1.68"
    const val MOKITO_ANDROID             = "org.mockito:mockito-android:4.3.1"
    const val MOKITO_KOTLIN              = "com.nhaarman.mockitokotlin2:mockito-kotlin:4.0.0"
    const val MOCKK                      = "io.mockk:mockk:1.12.3"

    // JDK 9
    const val jaxbApi                    = "javax.xml.bind:jaxb-api:2.3.1"
    const val jaxbCore                   = "com.sun.xml.bind:jaxb-core:2.3.0.1"
    const val jaxbImpl                   = "com.sun.xml.bind:jaxb-impl:2.3.2"
    const val jdk9Deps                   = "com.github.pengrad:jdk9-deps:1.0"


}

object GradlePlugins {
    // for third party lib vulnerability scan
    const val DEPENDENCY_SCAN            = "org.owasp:dependency-check-gradle:7.0.0"
    const val SPOTLESS                   = "com.diffplug.spotless:spotless-plugin-gradle:${Versions.SPOTLESS}"
    const val NAV_SAFE_ARGS              = "android.arch.navigation:navigation-safe-args-gradle-plugin:${Versions.NAV_SAFE_ARG}"
    const val ALL_OPEN                   = "org.jetbrains.kotlin:kotlin-allopen:1.3.72"
    const val AGP                        = "com.android.tools.build:gradle:7.1.2"
    const val KOTLIN                     = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.KOTLIN}"
}

object Repos {
    const val KOTLIN_EAP                 = "https://dl.bintray.com/kotlin/kotlin-eap"
}
