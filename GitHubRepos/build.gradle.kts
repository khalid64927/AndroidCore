import org.apache.tools.ant.taskdefs.condition.Os
// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        mavenCentral()
        maven {
            url = uri("https://maven.google.com")
        }
        maven{
            url = uri("https://dl.bintray.com/kotlin/kotlin-eap")
        }
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.72")
        // Add the Google Services plugin (check for v3.1.2 or higher).
        classpath ("com.google.gms:google-services:4.3.3")
    }


}

allprojects {
    apply(plugin = "org.owasp.dependencycheck")
    repositories {
        mavenCentral()
        maven {
            url = uri("https://maven.google.com")
        }
        maven{
            url = uri("https://dl.bintray.com/kotlin/kotlin-eap")
        }
    }

    configurations.all() {
        resolutionStrategy.force("org.antlr:antlr4-runtime:4.7.1")
        resolutionStrategy.force("org.antlr:antlr4-tool:4.7.1")
    }


    task<Exec>("createHooks") {
        doFirst {
            if (Os.isFamily(Os.FAMILY_WINDOWS)) {
                println("FAMILY_WINDOWS")
                commandLine ("${project.rootDir}/scripts/create-symlink.bat" , System.getProperty("user.dir"))
            }
            if (Os.isFamily(Os.FAMILY_UNIX)) {
                println("FAMILY_UNIX")
                commandLine ("${project.rootDir}/scripts/create-symlink.sh")
            }

        }

    }


}

/**
 * TODO: move this to configureOSSScan in ProjectBuildTask
 * Configuring Dependency Check plugin
 * 1. Maximum allowed vulnerabilities are no more than 7
 * 2. Report is generated at app/builds/reports/ in HTML format
*/
configure<org.owasp.dependencycheck.gradle.extension.DependencyCheckExtension> {
    format = org.owasp.dependencycheck.reporting.ReportGenerator.Format.HTML
    outputDirectory = "${project.buildDir}/reports"
    failBuildOnCVSS = 7f
}
// TODO: add dep update plugin
// https://github.com/ben-manes/gradle-versions-plugin