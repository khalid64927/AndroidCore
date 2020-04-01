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

import org.gradle.api.Action
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.accessors.runtime.addDependencyTo
import org.gradle.kotlin.dsl.exclude

/**
 * @author Mohammed Khalid Hamid
*/

open class Utility {
    val implementation            = "implementation"
    val kapt                      = "kapt"
    val testImplementation        = "testImplementation"
    val androidTestImplementation = "androidTestImplementation"

    fun DependencyHandler.unitTest() {
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
        testImplementation(Dependencies.CR_TEST)
    }

    fun DependencyHandler.UITest(){
        androidTestImplementation(Dependencies.NAV_TESTING_KTX)
        androidTestImplementation(Dependencies.RECYCLER_VIEW)
        androidTestImplementation(Dependencies.CARD_VIEW)
        androidTestImplementation(Dependencies.DESIGN)
        androidTestImplementation(Dependencies.JUNITX)
        androidTestImplementation(Dependencies.RECYCLER_VIEW)
        androidTestImplementation(Dependencies.GOOGLE_MATERIAL)

        androidTestImplementation(Dependencies.TEST_RULES)
        androidTestImplementation(Dependencies.ARCH_CORE_TESTING)
        androidTestImplementation(Dependencies.MOKITO_CORE)


        addConfigurationWithExclusion("androidTestImplementation",Dependencies.ESPRESSO_CORE, {
            exclude(group = "com.android.support", module = "support-annotations")
            exclude(group = "com.google.code.findbugs", module = "jsr305")
        })
        addConfigurationWithExclusion("androidTestImplementation",Dependencies.MOKITO_CORE,
            { exclude(group = "net.bytebuddy") })
    }

    @Suppress("UNUSED_PARAMETER")
    fun DependencyHandler.Dagger(){
        //TODO: library specific dependency functions

    }

    private fun DependencyHandler.implementation(dependencyName: String){
        addConfiguration(implementation,dependencyName)
    }

    private fun DependencyHandler.kapt(dependencyName: String){
        addConfiguration(kapt,dependencyName)
    }

    private fun DependencyHandler.testImplementation(dependencyName: String){
        addConfiguration(testImplementation,dependencyName)
    }

    private fun DependencyHandler.androidTestImplementation(dependencyName: String){
        addConfiguration(androidTestImplementation,dependencyName)
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
    private fun DependencyHandler.addConfiguration(configurationName: String, dependencyNotation: Any): Dependency? =
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
    private fun DependencyHandler.addConfigurationWithExclusion(
        configurationName: String,
        dependencyNotation: String,
        dependencyConfiguration: Action<ExternalModuleDependency>
    ): ExternalModuleDependency = addDependencyTo(
        this, configurationName, dependencyNotation, dependencyConfiguration
    ) as ExternalModuleDependency
}