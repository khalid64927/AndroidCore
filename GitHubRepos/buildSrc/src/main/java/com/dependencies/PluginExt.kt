package com.dependencies

import com.android.build.gradle.api.BaseVariant
import com.diffplug.gradle.spotless.SpotlessExtension
import org.gradle.api.Action
import org.gradle.api.DomainObjectSet
import org.gradle.api.Project
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.*
import org.gradle.kotlin.dsl.accessors.runtime.addDependencyTo
import org.gradle.testing.jacoco.tasks.JacocoReport
import org.jetbrains.kotlin.gradle.plugin.KaptExtension
import java.io.File

/**
 * @author Mohammed Khalid Hamid
 * This file has handy inline, inline extension function and constants used in this plugin
 */

object PluginConstants {
    const val implementation            = "implementation"
    const val kapt                      = "kapt"
    const val kaptTest                  = "kaptTest"
    const val kaptAndroidTest           = "kaptAndroidTest"
    const val api                       = "api"
    const val compileOnly               = "compileOnly"
    const val testImplementation        = "testImplementation"
    const val androidTestImplementation = "androidTestImplementation"
    val lintExclusion = mutableListOf("ObsoleteLintCustomCheck", // ButterKnife will fix this in v9.0
        "IconExpectedSize",
        "InvalidPackage", // Firestore uses GRPC which makes lint mad
        "NewerVersionAvailable", "GradleDependency", // For reproducible builds
        "SelectableText", "SyntheticAccessor")

    var enableBuildLogs = false
}

inline fun applyPlugins(isApp : Boolean, project: Project) = project.run {
    if(isApp){
        apply(plugin = "com.android.application")
        // TODO: add in demo : apply(plugin = "com.google.gms.google-services")
    } else {
        apply(plugin = "com.android.library")
        apply(plugin = "org.gradle.maven-publish")
    }
    apply(plugin = "kotlin-android")
    //apply(plugin = "com.google.devtools.ksp")
    apply(plugin = "kotlin-kapt")
    apply(plugin = "org.jetbrains.kotlin.plugin.allopen")
    apply(plugin = "androidx.navigation.safeargs.kotlin")
    apply(plugin = "com.diffplug.spotless")
    apply(plugin = "org.owasp.dependencycheck")
    if(isApp){
        apply(plugin = "dagger.hilt.android.plugin")
    }
}

inline fun getLintBaseline(project: Project, ext: KPluginExtensions) : File = project.run {
    val defaultLintFile = file("$rootDir/quality/lint-baseline.xml")
    val lintBaseLineFilePath = ext.lintBaseLineFilePath
    if(lintBaseLineFilePath.isEmpty()) return defaultLintFile
    return file(lintBaseLineFilePath)
}

inline fun configureSpotless(project: Project) = project.run {
    configure<SpotlessExtension>{
        kotlin {
            target ("**/*.kt")
            ktlint("0.44.0").userData(mapOf("disabled_rules" to "no-wildcard-imports"))
            licenseHeaderFile(project.rootProject.file("scripts/copyright.kt"))
        }
    }
}


/**
 * ========================================================================================
 * common deps extension functions start
 * ========================================================================================
 */
inline fun DependencyHandler.unitTest() {
    testImplementation(Dependencies.JUNIT)
    testImplementation(Dependencies.JUNIT_EXT)
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
    testImplementation(Dependencies.CR_TEST)
    testImplementation(Dependencies.MULTIDEXTEST)
    testImplementation(Dependencies.KLUENT)
    testImplementation(Dependencies.MOCKK)
    testImplementation(Dependencies.CR_TEST_DEBUG)
    testImplementation(Dependencies.CR_TEST)

    //TODO: check if you need JDK9 deps. JDK9()
}

inline fun DependencyHandler.JDK9(){
    compileOnly(Dependencies.jdk9Deps)
    kapt(Dependencies.jaxbApi)
    kapt(Dependencies.jaxbCore)
    kapt(Dependencies.jaxbImpl)
}

/**
 * 1. KOTLIN
 * 2. V7
 * 3. CONSTRAINT LAYOUT
 * 4. TIMBER
 * 5. RETROFIT ADAPTER
 * 6. RETROFIT GSON CONVERTER
 * 7. RETROFIT RUNTIME
 * 8. OKHTTP
 * 9. OKHTTP INTERCEPTOR
 * 10. CARD VIEW
 * 11. GOOGLE MATERIAL
 * 12. ANNOTATIONS
 *
 */
inline fun DependencyHandler.commonAndroidLibs(){
    implementation(Dependencies.KOTLIN)
    implementation(Dependencies.V7)
    implementation(Dependencies.CONSTRAINT_LAYOUT)
    implementation(Dependencies.TIMBER)
    implementation(Dependencies.SWIPEX)
    implementation(Dependencies.MULTIDEX)

    implementation(Dependencies.RETROFIT_ADAPTER)
    implementation(Dependencies.RETROFIT_GSON_CONVERTER)
    implementation(Dependencies.RETROFIT_RUNTIME)

    implementation(Dependencies.OKHTTP)
    implementation(Dependencies.OKHTTP_INTERCEPTOR)

    implementation(Dependencies.CARD_VIEW)
    implementation(Dependencies.GOOGLE_MATERIAL)
    implementation(Dependencies.ANNOTATIONS)
}


@Suppress("UNUSED_PARAMETER")
inline fun DependencyHandler.dagger(){
    //implementation(Dependencies.DAGGER_RUNTIME)
    api(Dependencies.DAGGER_ANDROID)
    //implementation(Dependencies.DAGGER_ANDROID_SUPPORT)
    kapt(Dependencies.DAGGER_ANDROID_PROCESSOR)
}

@Suppress("UNUSED_PARAMETER")
inline fun DependencyHandler.room(){
    implementation(Dependencies.ROOM_RUNTIME)
    implementation(Dependencies.ROOM_TESTING)
    //ksp(Dependencies.ROOM_COMPILER)
    implementation(Dependencies.ROOM_KTX)
}

inline fun DependencyHandler.lifeCycle(){
    // Lifecycle component
    implementation(Dependencies.LC_EXTENSION)
    implementation(Dependencies.LC_JAVA8)
    implementation(Dependencies.LC_RUNTIME)
    kapt(Dependencies.LC_COMPILER)
}

inline fun DependencyHandler.UITest(){
    androidTestImplementation(Dependencies.RECYCLER_VIEW)
    androidTestImplementation(Dependencies.CARD_VIEW)
    androidTestImplementation(Dependencies.GOOGLE_MATERIAL)
    androidTestImplementation(Dependencies.RECYCLER_VIEW)
    androidTestImplementation(Dependencies.GOOGLE_MATERIAL)

    androidTestImplementation(Dependencies.TEST_RULES)
    androidTestImplementation(Dependencies.JUNIT_EXT)
    androidTestImplementation(Dependencies.ARCH_CORE_TESTING)
    androidTestImplementation(Dependencies.SWIPEX)
    androidTestImplementation(Dependencies.MULTIDEXTEST)
    androidTestImplementation(Dependencies.MOCKK)

    addConfigurationWithExclusion("androidTestImplementation",Dependencies.ESPRESSO_CORE) {
        exclude(group = "com.android.support", module = "support-annotations")
        exclude(group = "com.google.code.findbugs", module = "jsr305")
    }
    addConfigurationWithExclusion("androidTestImplementation",Dependencies.MOKITO_CORE
    ) { exclude(group = "net.bytebuddy") }
}
/**
 * ========================================================================================
 * common deps extension functions end
 * ========================================================================================
 */



/**
 * ========================================================================================
 * plugin configuration extension functions start
 * ========================================================================================
 */
@Deprecated(
    message = "Use KSP instead for all kotlin annotation " +
            "processing url:https://github.com/google/ksp")
inline fun KaptExtension.configureKapt(){
    correctErrorTypes = true
    arguments {
        arg("-Xjvm-default", "all")
        arg("dagger.validateTransitiveComponentDependencies", "DISABLED")
    }
    //jvmTarget = "11"
    javacOptions {
        // Increase the max count of errors from annotation processors.
        // Default is 100.
        option("-Xmaxerrs", 1000)
    }
}

inline fun configureJacoco(
    project: Project,
    variants: DomainObjectSet<out BaseVariant>,
    options: JacocoOptions
) = project.run {
    pln("configureJacoco 1")
    variants.all {
        val variantName = name
        pln("configureJacoco 1$variantName")
        val isDebuggable = true
        if (!isDebuggable) {
            project.logger.info("Skipping Jacoco for $name because it is not debuggable.")
            pln("configureJacoco 2$isDebuggable")
            return@all
        }
        pln("configureJacoco 33")
        project.tasks.register<JacocoReport>("jacoco${variantName.capitalize()}Report") {
            dependsOn(project.tasks["test${variantName.capitalize()}UnitTest"])
            val coverageSourceDirs = "src/main/java"
            pln("configureJacoco 3")

            val javaClasses = project
                .fileTree("${project.buildDir}/intermediates/javac/$variantName") {
                    setExcludes(options.excludes)
                }

            val kotlinClasses = project
                .fileTree("${project.buildDir}/tmp/kotlin-classes/$variantName") {
                    setExcludes(options.excludes)
                }

            // Using the default Jacoco exec file output path.
            val execFile = "jacoco/test${variantName.capitalize()}UnitTest.exec"

            executionData.setFrom(
                project.fileTree("${project.buildDir}") {
                    setIncludes(listOf(execFile))
                }
            )

            // Do not run task if there's no execution data.
            setOnlyIf { executionData.files.any { it.exists() } }
            classDirectories.setFrom(javaClasses, kotlinClasses)
            sourceDirectories.setFrom(coverageSourceDirs)
            additionalSourceDirs.setFrom(coverageSourceDirs)
            reports.xml.required.set(true)
            reports.html.required.set(true)
            pln("configureJacoco 4")
        }
    }
}
/**
 * ========================================================================================
 * plugin configuration extension functions end
 * ========================================================================================
 */


/**
 * ========================================================================================
 * basic dep inline functions end
 * ========================================================================================
 */
inline fun DependencyHandler.fragment(){
    implementation(Dependencies.FRAGMENT_TESTING)
}

inline fun DependencyHandler.implementation(dependencyName: String){
    addConfiguration(PluginConstants.implementation,dependencyName)
}

inline fun DependencyHandler.api(dependencyName: String){
    addConfiguration(PluginConstants.api,dependencyName)
}

inline fun DependencyHandler.compileOnly(dependencyName: String){
    addConfiguration(PluginConstants.compileOnly,dependencyName)
}

inline fun DependencyHandler.kapt(dependencyName: String){
    addConfiguration(PluginConstants.kapt,dependencyName)
}

inline fun DependencyHandler.kaptAndroidTest(dependencyName: String){
    addConfiguration(PluginConstants.kaptAndroidTest,dependencyName)
}

inline fun DependencyHandler.kaptTest(dependencyName: String){
    addConfiguration(PluginConstants.kaptTest,dependencyName)
}

inline fun DependencyHandler.testImplementation(dependencyName: String){
    addConfiguration(PluginConstants.testImplementation,dependencyName)
}

inline fun DependencyHandler.androidTestImplementation(dependencyName: String){
    addConfiguration(PluginConstants.androidTestImplementation,dependencyName)
}

/**
 * Adds a dependency
 *
 * @param configurationName configuration to be set for this dependency
 * @param dependencyNotation notation for the dependency to be added.
 * @return The dependency.
 *
 * @see [DependencyHandler.add]
 */
inline fun DependencyHandler.addConfiguration(configurationName: String, dependencyNotation: Any): Dependency? =
    add(configurationName, dependencyNotation)


/**
 * Adds a dependency
 *
 * @param configurationName configuration to be set for this dependency
 * @param dependencyNotation notation for the dependency to be added.
 * @param dependencyConfiguration expression to use to configure the dependency.
 * @return The dependency.
 *
 * @see [DependencyHandler.add]
 */
inline  fun DependencyHandler.addConfigurationWithExclusion(
    configurationName: String,
    dependencyNotation: String,
    dependencyConfiguration: Action<ExternalModuleDependency>
): ExternalModuleDependency = addDependencyTo(
    this, configurationName, dependencyNotation, dependencyConfiguration
)

inline fun pln(msg: String){
    if(PluginConstants.enableBuildLogs){
        println(msg)
    }
}