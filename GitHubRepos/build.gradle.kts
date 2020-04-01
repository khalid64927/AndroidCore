// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()
        maven {
            url = uri("https://maven.fabric.io/public")
        }

        maven{
            url = uri("https://dl.bintray.com/kotlin/kotlin-eap")
        }
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.60-eap-25")
        // Add the Google Services plugin (check for v3.1.2 or higher).
        classpath ("com.google.gms:google-services:4.3.3")
        // Add the Fabric Crashlytics plugin.
        classpath ("io.fabric.tools:gradle:1.31.2")



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
}
