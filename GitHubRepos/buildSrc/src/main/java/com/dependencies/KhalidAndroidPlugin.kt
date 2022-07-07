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

open class KhalidAndroidPlugin : Plugin<Project> {

    lateinit var ext: KPluginExtensions


    /**
     * Determines if a Project is the 'library' module
     */
    val Project.isLibrary get() = name == "app"
    private val Project.configDir get() = "$rootDir/quality"

    override fun apply(target: Project) {
        if(target.properties.entries.contains("enableBuildLogs")){
            PluginConstants.enableBuildLogs = true
        } else {
            println("========================================================================")
            println("add enableBuildLogs=true in gradle.properties to print plugin build logs")
            println("========================================================================")
        }
        ext = target.extensions.create("KPlugin")
        applyPlugins((target.name == "app"), target)
        pln("name "+ target.name)
        configureAndroid(target)
        configureQuality(target)
        configureSpotless(target)
        target.configureGlobalTasks()
        pln("ext  ..after "+ ext.compileSDK)
    }

    private fun Project.configureGlobalTasks() {
        tasks.register<ProjectBuildTask>("spotless")
        tasks.register<ProjectBuildTask>("unitTest")
        tasks.register<ProjectBuildTask>("uiTest")
        tasks.register<ProjectBuildTask>("allTests")
        tasks.register<ProjectBuildTask>("depCheck")
        tasks.register<ProjectBuildTask>("sonar")
    }

    private fun configureAllOpen(project: Project) = project.run {
        try {
            if(ext.openAnnotationPath.isEmpty()){
                throw java.lang.IllegalStateException(" missing openAnnotationPath value")
            }
            val allOpenExt = extensions.getByName("allOpen") as org.jetbrains.kotlin.allopen.gradle.AllOpenExtension
            allOpenExt.annotation("com.khalid.hamid.githubrepos.testing.OpenClass")
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    private fun configureKotlin(project: Project) = project.run {
        configure<KaptExtension> {
            configureKapt()
        }
    }

    private fun configureAndroid(project: Project) {
        configureKotlin(project)
        configureAndroidDefaults(project)
    }

    private fun configureAndroidDefaults(project: Project) = project.run {
        configure<BaseExtension>{
            pln(" compileSDK "+ ext.compileSDK)
            compileSdkVersion(ext.compileSDK.toInt())
            buildToolsVersion(ext.buildTools)
            defaultConfig {
                pln(" min sdk "+ ext.minSDK)
                pln(" targetSDK "+ ext.targetSDK)
                pln(" testRunner  "+ ext.testRunner)
                pln(" lintExclusionRules "+ ext.lintExclusionRules)
                pln(" isLibraryModule "+ ext.isLibraryModule)
                pln(" lintExclusionRules "+ ext.lintExclusionRules.toString())
                minSdk = ext.minSDK
                multiDexEnabled = true
                targetSdk = ext.targetSDK.toInt()
                versionName = ext.versionName
                versionCode = ext.versionCode
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
                baselineFile = getLintBaseline(project, ext)
                isCheckAllWarnings = true
                isWarningsAsErrors = true
                isAbortOnError = false // TODO: fix lint issues
                disable("InvalidPackage")
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

            sourceSets {
                val sharedResTestDir = "src/sharedTest/resources"
                val sharedUnitTestSrc = listOf("src/sharedTest/java", "src/sharedTest/kotlin", "src/test/kotlin")
                val sharedUITestSrc = listOf("src/sharedTest/java", "src/sharedTest/kotlin", "src/androidTest/kotlin")
                this.getByName("androidTest"){
                    java.srcDirs(sharedUITestSrc)
                    resources.srcDirs(sharedResTestDir)
                }

                this.getByName("test"){
                    java.srcDirs(sharedUnitTestSrc)
                    resources.srcDirs(sharedResTestDir)
                }
            }
        }

    }

    private fun configureQuality(target: Project) = target.run {
        val project = this
        configureCheckStyle(target)
        // set jacoco config
        afterEvaluate {
            pln("afterEvaluate")
            extensions.getByType(KPluginExtensions::class.java).run {
                val jacocoOptions = this.jacoco
                if (jacocoOptions.isEnabled) {
                    // Setup jacoco tasks to generate coverage report for this module.
                    plugins.apply(JacocoPlugin::class.java)
                    plugins.all {
                        when (this) {
                            is LibraryPlugin -> {
                                extensions.getByType(LibraryExtension::class.java).run {
                                    configureJacoco(project, libraryVariants, jacocoOptions)
                                }
                            }
                            is AppPlugin -> {
                                extensions.getByType(AppExtension::class.java).run {
                                    configureJacoco(project, applicationVariants, jacocoOptions)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun configureCheckStyle(target: Project) = target.run {
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
}