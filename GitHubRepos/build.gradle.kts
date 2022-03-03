// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        //google()
        maven {
            url = uri("https://maven.google.com")
        }
        jcenter()
        maven {
            url = uri("https://maven.fabric.io/public")
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
    repositories {
        google()
        jcenter()
        maven{
            url = uri("https://dl.bintray.com/kotlin/kotlin-eap")
        }
    }

    configurations.all() {
        resolutionStrategy.force("org.antlr:antlr4-runtime:4.7.1")
        resolutionStrategy.force("org.antlr:antlr4-tool:4.7.1")
    }


    task<Exec>("createHooks") {
        if (org.apache.tools.ant.taskdefs.condition.Os.isFamily(org.apache.tools.ant.taskdefs.condition.Os.FAMILY_WINDOWS)) {
            commandLine ("${project.rootDir}/scripts/create-symlink.bat" , System.getProperty("user.dir"))
        }
        if (org.apache.tools.ant.taskdefs.condition.Os.isFamily(org.apache.tools.ant.taskdefs.condition.Os.FAMILY_UNIX)) {
            commandLine ("${project.rootDir}/scripts/create-symlink.sh")
        }
    }


}


