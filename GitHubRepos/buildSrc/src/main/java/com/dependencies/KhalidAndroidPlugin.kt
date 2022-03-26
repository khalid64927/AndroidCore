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

import com.android.build.gradle.*
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.quality.Checkstyle
import org.gradle.api.plugins.quality.CheckstyleExtension
import org.gradle.kotlin.dsl.*
import org.gradle.testing.jacoco.plugins.JacocoPlugin
import org.jetbrains.kotlin.gradle.plugin.KaptExtension

open class KhalidAndroidPlugin : Plugin<Project>, Utility() {
    /**
     * Determines if a Project is the 'library' module
     */
    val Project.isLibrary get() = name == "app"
    private val Project.configDir get() = "$rootDir/quality"
    private var lintExclusionRules = arrayListOf("ObsoleteLintCustomCheck", // ButterKnife will fix this in v9.0
        "IconExpectedSize",
        "InvalidPackage", // Firestore uses GRPC which makes lint mad
        "NewerVersionAvailable", "GradleDependency", // For reproducible builds
        "SelectableText", "SyntheticAccessor")

    override fun apply(target: Project) {
        ext = target.extensions.create<KPluginExtensions>("KPlugin")
        target.applyPlugins((target.name == "app"))
        println("name "+ target.name)
        //TODO: unable to use extension property in apply function
        target.configureAndroid()
        target.configureQuality()
        println("ext  ..after "+ ext.compileSDK)
        target.afterEvaluate {
            println("afterEvaluate")
            target.extensions.getByType(KPluginExtensions::class.java).run {
                val jacocoOptions = this.jacoco
                if (jacocoOptions.isEnabled) {
                    // Setup jacoco tasks to generate coverage report for this module.
                    target.plugins.apply(JacocoPlugin::class.java)
                    target.plugins.all {
                        when (this) {
                            is LibraryPlugin -> {
                                target.extensions.getByType(LibraryExtension::class.java).run {
                                    configureJacoco(target, libraryVariants, jacocoOptions)
                                }
                            }
                            is AppPlugin -> {
                                target.extensions.getByType(AppExtension::class.java).run {
                                    configureJacoco(target, applicationVariants, jacocoOptions)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    fun Project.configureKotlin(){
        configure<KaptExtension> {
            configureKapt()
        }
    }

    private fun Project.configureAndroid() {
        configureKotlin()
        configure<BaseExtension>{
            println(" compileSDK "+ ext.compileSDK)
            compileSdkVersion(ext.compileSDK.toInt())
            buildToolsVersion(ext.buildTools)
            defaultConfig {
                println(" min sdk "+ ext.minSDK)
                println(" targetSDK "+ ext.targetSDK)
                println(" testRunner  "+ ext.testRunner)
                println(" lintExclusionRules "+ ext.lintExclusionRules)
                println(" isLibraryModule "+ ext.isLibraryModule)
                println(" lintExclusionRules "+ ext.lintExclusionRules.toString())
                minSdk = ext.minSDK
                multiDexEnabled = true
                targetSdk = ext.targetSDK.toInt()
                versionName = ext.versionName
                versionCode = ext.versionCode
                //TODO: dataBinding.isEnabledForTests = true
                vectorDrawables.useSupportLibrary = true
                testInstrumentationRunner = ext.testRunner

                val schemas = "${projectDir}/schemas"
                javaCompileOptions {
                    annotationProcessorOptions {
                        arguments.putAll(mapOf("room.schemaLocation" to schemas,
                            "room.expandProjection" to "true",
                            "androidx.room.RoomProcessor" to "true",
                            "dagger.validateTransitiveComponentDependencies" to "DISABLED",
                            "dagger.gradle.incremental" to "true"))
                    }
                }
            }

            dataBinding.enable = true
            dataBinding.enableForTests = true
            dataBinding.addKtx = true

            buildFeatures.viewBinding = true

            lintOptions {
                baselineFile = getLintBaseline()
                isCheckAllWarnings = true
                isWarningsAsErrors = true
                isAbortOnError = true
            }
            buildTypes {
                getByName("release") {
                    isMinifyEnabled = true
                    if(ext.isLibraryModule){
                        consumerProguardFile("proguard-android.txt")
                    }else{
                        proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
                    }
                }
                getByName("debug"){
                    isDebuggable = true
                }
            }
            packagingOptions {
                resources.excludes.add("LICENSE.txt")
                resources.excludes.add("META-INF/rxjava.properties")
                resources.excludes.add("META-INF/DEPENDENCIES")
                resources.excludes.add("META-INF/LICENSE")
                resources.excludes.add("META-INF/LICENSE.txt")
                resources.excludes.add("META-INF/license.txt")
                resources.excludes.add("META-INF/NOTICE")
                resources.excludes.add("META-INF/NOTICE.txt")
                resources.excludes.add("META-INF/notice.txt")
                resources.excludes.add("META-INF/ASL2.0")
            }
            testOptions.unitTests.isReturnDefaultValues = true
            testOptions.unitTests.isIncludeAndroidResources = true
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_11
                targetCompatibility = JavaVersion.VERSION_11
            }

            dependencies {
                unitTest()
                UITest()
            }
        }
    }

    // TODO: add checkstyle
    private fun Project.configureQuality() {
        apply(plugin = "checkstyle")

        configure<CheckstyleExtension> { toolVersion = "8.10.1" }
        tasks.named("check").configure { dependsOn("checkstyle") }

        tasks.register<Checkstyle>("checkstyle") {
            var path = ext.checkstylePath
            if(path.isEmpty()){
                path = "${project.configDir}/checkstyle.xml"
            }
            configFile = file(path)
            source("src")
            include("**/*.java")
            exclude("**/gen/**")
            classpath = files()
        }
    }

    // TODO: publish plugin
    internal fun Project.configurePlugins() {
        plugins.apply("com.android.library")
        plugins.apply("org.gradle.maven-publish") // or anything else, that you would like to load
    }

}