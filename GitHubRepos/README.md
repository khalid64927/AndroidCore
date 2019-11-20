GoJek Coding Challenge

#@Author : [Mohammed Khalid Hamid]

*The project is in Kotlin
*Uses MVVM + Clean code Architecture
*Uses Co-routines for network calls
*Uses LiveData for UI update
* Built on Android Studio 4.0 Canary 3 build
* Android SDK 29
* Gradle 5.6.1
* Android Gradle Plugin 1.3.60-eap-25


### Functionality

App shows list of github repos with Local DB as single source of truth.

Swipe to refresh will update the list

#### RepoFragment
This fragment displays the list of a repository


#### Building
##[The project uses Gradle KTS script]
## The project is in Kotlin

## APK can be found in [release/apks]  
## Screenshots can be found in  [release/apks]  



### Testing
The project uses both instrumentation tests that run on the device
and local unit tests that run on your computer.
To run both of them and generate a coverage report, you can run:



#### Device Tests
##### UI Tests
The projects uses Espresso for UI testing. Since each fragment
is limited to a ViewModel, each test mocks related ViewModel to
run the tests.
##### Database Tests
The project creates an in memory database for each database test but still
runs them on the device.

#### Local Unit Tests
##### ViewModel Tests
Each ViewModel is tested using local unit tests with mock Repository
implementations.
##### Repository Tests
Each Repository is tested using local unit tests with mock web service and
mock database.
##### Webservice Tests
The project uses [MockWebServer][mockwebserver] and Fake implementations


### Libraries
* [Android Support Library][support-lib]
* [Android Architecture Components][arch]
* [Android Data Binding][data-binding]
* [Dagger 2][dagger2] for dependency injection
* [Retrofit][retrofit] for REST api communication
* [Glide][glide] for image loading
* [Timber][timber] for logging
* [espresso][espresso] for UI tests
* [mockito][mockito] for mocking in tests




