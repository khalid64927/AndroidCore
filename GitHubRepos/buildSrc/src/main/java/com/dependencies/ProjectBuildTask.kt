package com.dependencies
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.configure

abstract class ProjectBuildTask: org.gradle.api.tasks.Exec() {

    @TaskAction
    fun configureOSSScan(){
        // TODO:
    }

}
