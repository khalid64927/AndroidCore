import org.apache.tools.ant.taskdefs.condition.Os
// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        mavenCentral()
        google()
        maven{
            url = uri("https://dl.bintray.com/kotlin/kotlin-eap")
        }
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.10")
        // Add the Google Services plugin (check for v3.1.2 or higher).
        classpath ("com.google.gms:google-services:4.3.10")
    }

}

allprojects {
    repositories {
        mavenCentral()
        google()
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
