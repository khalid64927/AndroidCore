package com.dependencies
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

abstract class ProjectBuildTask: DefaultTask() {

    private val runtime: Runtime = Runtime.getRuntime()

    @TaskAction
    fun configureOSSScan(){
        runtime.exec("./gradlew app:dependencyCheckAnalyze --info")
    }

    @TaskAction
    fun runSpotless(){
        pln("runSpotless")
        runtime.exec("./gradlew :app:spotlessApply")
    }

    @TaskAction
    fun runSonar(){
        // TODO:
    }

    @TaskAction
    fun runDepUpdates(){
        runtime.exec("./gradlew app:dependencyUpdates")
    }

    @TaskAction
    fun runUnitTest(){
        pln("runUnitTest")
        runtime.exec("./gradlew :app:testMockDebugUnitTest")
    }

    @TaskAction
    fun runUITest(){
        // TODO:
    }

    @TaskAction
    fun runAllTest(){
        runUnitTest()
        runUITest()
    }
}

