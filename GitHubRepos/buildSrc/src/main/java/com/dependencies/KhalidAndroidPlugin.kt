/*
 * Copyright 2020 Mohammed Khalid Hamid.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dependencies

import com.android.build.gradle.BaseExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.quality.Checkstyle
import org.gradle.api.plugins.quality.CheckstyleExtension
import org.gradle.kotlin.dsl.*

open class KhalidAndroidPlugin : Plugin<Project>, Utility() {
    /**
     * Determines if a Project is the 'library' module
     */
    val Project.isLibrary get() = name == "app"
    val Project.configDir get() = "$rootDir/quality"
    override fun apply(target: Project) {
       // target.configurePlugins()
        target.configureAndroid()
        target.configureQuality()
    }

    fun Project.configureAndroid() {
        if (name == "app") {
            apply(plugin = "com.android.application")
            apply(plugin = "io.fabric")
        } else {
            apply(plugin = "com.android.library")
        }
        apply(plugin = "kotlin-android")
        apply(plugin = "org.jetbrains.kotlin.plugin.allopen")
        apply(plugin = "androidx.navigation.safeargs")
        apply(plugin = "jacoco")
       // apply(plugin = "com.diffplug.gradle.spotless")
        apply(plugin = "kotlin-kapt")
        apply(plugin = "kotlin-android-extensions")



        configure<BaseExtension>{

            compileSdkVersion(Versions.MAX_SDK)
            defaultConfig {
                minSdkVersion(Versions.MIN_SDK)
                targetSdkVersion(Versions.MAX_SDK)
                versionName = Versions.VERSION_NAME
                versionCode = Versions.VERSION_CODE
                dataBinding.isEnabled = true
                dataBinding.isEnabledForTests = true
                vectorDrawables.useSupportLibrary = true
                testInstrumentationRunner = "com.khalid.hamid.githubrepos.utilities.AppTestRunner"
            }

            lintOptions {
                disable(
                    "ObsoleteLintCustomCheck", // ButterKnife will fix this in v9.0
                    "IconExpectedSize",
                    "InvalidPackage", // Firestore uses GRPC which makes lint mad
                    "NewerVersionAvailable", "GradleDependency", // For reproducible builds
                    "SelectableText", "SyntheticAccessor" // We almost never care about this
                )

                isCheckAllWarnings = true
                isWarningsAsErrors = true
                isAbortOnError = true
                baselineFile = file("$configDir/lint-baseline.xml")
            }

            buildTypes {
                getByName("release") {
                    isMinifyEnabled = true
                    if(name == "app"){
                        proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
                    }else{
                        consumerProguardFile("proguard-android.txt")
                    }
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

            testOptions.unitTests.isReturnDefaultValues = true
            testOptions.unitTests.isIncludeAndroidResources = true

            compileOptions {
                setSourceCompatibility(JavaVersion.VERSION_1_8)
                setTargetCompatibility(JavaVersion.VERSION_1_8)
            }

            dependencies {
                unitTest()
                UITest()
            }
        }
    }

    fun Project.configureQuality() {
        apply(plugin = "checkstyle")

        configure<CheckstyleExtension> { toolVersion = "8.10.1" }
        tasks.named("check").configure { dependsOn("checkstyle") }

        tasks.register<Checkstyle>("checkstyle") {
            configFile = file("${project.configDir}/checkstyle.xml")
            source("src")
            include("**/*.java")
            exclude("**/gen/**")
            classpath = files()
        }
    }

    internal fun Project.configurePlugins() {
        plugins.apply("com.android.library")
        plugins.apply("org.gradle.maven-publish") // or anything else, that you would like to load
    }
}