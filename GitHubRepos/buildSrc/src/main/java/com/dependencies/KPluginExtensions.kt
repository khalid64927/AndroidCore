package com.dependencies

open class KPluginExtensions() {

    var modules = listOf<String>()
    var isLibraryModule = false
    var exclusions = ""
    var compileSDK = "android-R"
    var targetSDK = "R"
    var minSDK : Int = 19
    var versionCode : Int = 10
    var versionName = "1.1"
    var testRunner = "androidx.test.runner.AndroidJUnitRunner"
    var lintBaseLineFilePath = ""
    //var testRunner = "com.khalid.hamid.githubrepos.utilities.AppTestRunner"
    var lintExclusionRules : List<String> = emptyList()
    var checkstylePath = ""




}