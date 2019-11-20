package com.dependencies

object Versions {
    const val RETROFIT = "2.6.0"
    const val OKHTTP_INTERCEPTOR = "3.8.1"
    const val RXJAVA = "2.1.0"
    const val MIN_SDK = 19
    const val BUILD_TOOLS = "29.0.1"
    const val MAX_SDK = 29
    const val LIFECYCLE = "2.0.0-rc01"
    const val ROOM = "2.2.0-alpha01"
    const val FRAGMENT = "1.1.0-alpha07"
    const val NAV = "2.1.0-alpha02"
    const val NAV_TESTING = "1.0.0-alpha03"
    const val KTX = "1.2.0-beta01"
    const val EXT_KOTLIN_RUNNER = "1.1.1-beta01"
    const val TEST_RULE = "1.2.0-beta01"
}

object Dependencies {
    const val ANNOTATIONS = "androidx.annotation:annotation:1.0.0"
    const val V7 = "androidx.appcompat:appcompat:1.0.0"
    const val DESIGN = "com.google.android.material:material:1.0.0"
    const val RECYCLER_VIEW = "androidx.recyclerview:recyclerview:1.0.0"
    const val CARD_VIEW = "androidx.cardview:cardview:1.0.0"
    const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:2.0.0-alpha3"
    // Add below for Dagger
    const val DAGGER_RUNTIME = "com.google.dagger:dagger:2.21"
    const val DAGGER_ANDROID = "com.google.dagger:dagger-android:2.21"
    const val DAGGER_ANDROID_SUPPORT = "com.google.dagger:dagger-android-support:2.21"
    const val DAGGER_COMPILER = "com.google.dagger:dagger-compiler:2.21"
    // kapt here
    const val DAGGER_ANDROID_PROCESSOR = "com.google.dagger:dagger-android-processor:2.21"
    // OKHTTP
    const val OKHTTP_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:${Versions.OKHTTP_INTERCEPTOR}"
    const val OKHTTP = "com.squareup.okhttp3:okhttp:${Versions.OKHTTP_INTERCEPTOR}"
    // Add below for Dagger
    const val RETROFIT_RUNTIME = "com.squareup.retrofit2:retrofit:${Versions.RETROFIT}"
    const val RETROFIT_GSON_CONVERTER = "com.squareup.retrofit2:converter-gson:${Versions.RETROFIT}"
    const val RETROFIT_ADAPTER = "com.squareup.retrofit2:adapter-rxjava2:${Versions.RETROFIT}"

    const val TIMBER = "com.jakewharton.timber:timber:4.6.0"
    const val RX_ANDROID = "io.reactivex.rxjava2:rxandroid:${Versions.RXJAVA}"
    const val GOOGLE_MATERIAL = "com.google.android.material:material:1.0.0"
    const val GLIDE_RUNTIME = "com.github.bumptech.glide:glide:4.8.0"
    const val GLIDE_COMPILER = "com.github.bumptech.glide:compiler:4.8.0"
    const val JUNITX = "androidx.test.ext:junit:1.1.0"
    const val JUNIT = "junit:junit:4.12"
    const val ESP_IDL = "androidx.test.espresso:espresso-idling-resource:3.1.1"
    const val ESP_CONTRIBUTE = "androidx.test.espresso:espresso-contrib:3.1.1"
    const val ESP_INTENTS = "androidx.test.espresso:espresso-intents:3.1.1"
    const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:3.1.1"
    const val MOKITO_CORE = "org.mockito:mockito-core:2.24.5"
    const val MOKITO_ALL = "org.mockito:mockito-all:2.0.2-beta"
    const val MOKITO_INLINE = "org.mockito:mockito-inline:2.13.0"
    const val MOCKWEBSERVER = "com.squareup.okhttp3:mockwebserver:${Versions.OKHTTP_INTERCEPTOR}"
    const val KOTLIN = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.60-eap-25"
    const val SHIMMER = "com.facebook.shimmer:shimmer:0.5.0"
    const val LC_RUNTIME = "androidx.lifecycle:lifecycle-runtime:${Versions.LIFECYCLE}"
    const val LC_COMPILER = "androidx.lifecycle:lifecycle-compiler:${Versions.LIFECYCLE}"
    const val LC_JAVA8 = "android.arch.lifecycle:common-java8:${Versions.LIFECYCLE}"
    const val LC_EXTENSION = "androidx.lifecycle:lifecycle-extensions:${Versions.LIFECYCLE}"
    const val ROOM_RUNTIME = "androidx.room:room-runtime:${Versions.ROOM}"
    const val ROOM_COMPILER = "androidx.room:room-compiler:${Versions.ROOM}"
    const val ROOM_KTX = "androidx.room:room-ktx:${Versions.ROOM}"
    const val ROOM_TESTING = "androidx.room:room-testing:${Versions.ROOM}"
    const val ARCH_CORE_TESTING = "androidx.arch.core:core-testing:2.0.0-rc01"
    const val NAV_RUNTIME_FRAGMENT_KTX = "androidx.navigation:navigation-fragment-ktx:${Versions.NAV}"
    const val NAV_TESTING_KTX = "android.arch.navigation:navigation-testing-ktx:${Versions.NAV_TESTING}"
    const val FRAGMENT_TESTING = "androidx.fragment:fragment-testing:${Versions.FRAGMENT}"
    const val TEST_CORE = "androidx.test:core:${Versions.KTX}"
    const val FRAGMENT = "androidx.fragment:fragment:${Versions.FRAGMENT}"
    // testing
    const val TEST_KTX_CORE = "androidx.test:core-ktx:1.2.0-beta01"
    const val TEST_KTX_JUNIT = "androidx.test.ext:junit-ktx:${Versions.EXT_KOTLIN_RUNNER}"
    const val TEST_RULES = "androidx.test:rules:${Versions.TEST_RULE}"
    const val CR_TEST = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.2.1"
}
