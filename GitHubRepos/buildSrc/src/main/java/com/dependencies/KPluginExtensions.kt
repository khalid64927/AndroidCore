package com.dependencies

import org.gradle.api.Action

open class KPluginExtensions {

    companion object {
        const val name = "KPlugin"
    }

    var modules = listOf<String>()
    var isLibraryModule = false
    var exclusions = ""
    var compileSDK = "34"
    var buildTools = "34.0.0"
    var targetSDK = "34"
    var minSDK : Int = 21
    var versionCode : Int = 10
    var versionName = "1.1"
    var testRunner = "com.khalid.hamid.githubrepos.utilities.AppTestRunner"
    var lintBaseLineFilePath = ""
    var lintExclusionRules : List<String> = emptyList()
    var checkstylePath = ""
    /** TODO: not yet working
     * provide class path to your all open annotation in your source code
     * Ex: com.khalid.hamid.githubrepos.testing.OpenClass
    */
    var openAnnotationPath = "com.khalid.hamid.githubrepos.testing.OpenClass"


    open val jacoco: JacocoOptions = JacocoOptions()
    open fun jacoco(action: Action<JacocoOptions>) {
        action.execute(jacoco)
    }
}

open class JacocoOptions {
    open var isEnabled: Boolean = true
    val coverageExclusions = mutableListOf(
        // Android
        "**/R.class",
        "**/R\$*.class",
        "**/BuildConfig.*",
        "**/Manifest*.*"
    )
    open var excludes: MutableList<String> = coverageExclusions
    open var dependentTasklist: ArrayList<String> = arrayListOf()
    open fun excludes(excludes: List<String>) {
        this.excludes.addAll(excludes)
    }
    open fun dependsOnTasks(vararg dependentTasks : String){
        this.dependentTasklist.addAll(dependentTasks)
    }
}