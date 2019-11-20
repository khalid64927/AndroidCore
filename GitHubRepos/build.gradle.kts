// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {

    repositories {
        google()
        jcenter()
        maven{
            url = uri("https://dl.bintray.com/kotlin/kotlin-eap")
        }
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
