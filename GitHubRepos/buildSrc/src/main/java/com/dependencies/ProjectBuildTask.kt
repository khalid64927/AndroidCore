package com.dependencies

import org.gradle.api.tasks.TaskAction

abstract class ProjectBuildTask: org.gradle.api.tasks.Exec() {

    @TaskAction
    fun taskAction(){
        // TODO:
    }

}
