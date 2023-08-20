package com.dependencies.tasks

import com.dependencies.pln
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.options.Option

abstract class ProjectBuildTask: DefaultTask() {

    private val runtime: Runtime = Runtime.getRuntime()
    private var taskType: String = ""


    @Option(option = "url", description = "Configures the URL to be verified.")
    open fun setTaskType(taskType: String) {
        this.taskType = taskType
    }

    @Input
    open fun getTaskType(): String? {
        return taskType
    }

    @TaskAction
    fun runAll(taskString: String){
        logger.quiet("running task ")
        taskString.checkTask()
    }

    private fun String.checkTask(){
        when(this.lowercase()){
            "spotless"  -> runSpotless()
            "sonar" -> runSonar()
            "oss" -> runOSSScan()
            "depsupdate" -> runDepUpdates()
            "alltest" -> runAllTest()
            else -> {
                logger.quiet("option not recognized")
                logger.quiet("supported option are")
                logger.quiet("spotless, sonar, oss, depsupdate, alltest")
            }
        }
    }

    private fun runOSSScan() {
        runtime.exec("./gradlew app:dependencyCheckAnalyze --info")
    }

    private fun runSpotless() {
        pln("runSpotless")
        runtime.exec("./gradlew :app:spotlessApply")
    }

    private fun runLint() {
        pln("runLint")
        runtime.exec("./gradlew :app:lintProdDebug")
    }

    private fun runSonar() {
        pln("runSonar")
        runtime.exec("./gradlew jacocoTestReport --stacktrace")
    }

    private fun runDepUpdates() {
        pln("runDepUpdates")
        runtime.exec("./gradlew app:dependencyUpdates")
    }

    private fun runUnitTest() {
        pln("runUnitTest")
        runtime.exec("./gradlew :app:testMockDebugUnitTest")
    }

    private fun runUITest() {
        // TODO:
    }

    private fun runAllTest() {
        runUnitTest()
        runUITest()
    }
}