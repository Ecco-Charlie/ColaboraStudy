<h1 align="center">
	<img src="composeApp/src/androidMain/res/mipmap-xxxhdpi/ic_launcher.png" alt="ColaboraStudy Logo"/>
	<p>ColaboraStudy</p>
</h1>

**ColaboraStudy** is a *multiplatform* *collaborative learning* application that uses the Artificial Intelligence of [Google Gemini](https://gemini.google/mx/about/) to automatically generate practice exams.  
The system allows *study content to be transformed into dynamic assessments*, making *group study* immediate and efficient.

Its architecture is designed to *overcome connectivity limitations*: only one person needs internet access to *generate an exam* and distribute it to others through a local network (intranet).  
Additionally, it allows exporting and importing exams, ensuring that study material is always available ***without** relying on the cloud*.

### Benefits
* **AI Efficiency:** Automatic generation of intelligent questions.
* **Hybrid Mode:** Offline group connection using a single internet access point.
* **Portability:** Export and import files to study anytime.
* **Digital Inclusion:** Works perfectly in environments with unstable or limited connectivity.

### Screenshots
#### Home
<p align="center">
	<img src="screenshots/screenshotAndroidHome.jpg" alt="Home" height="350"/>
	<img src="screenshots/screenshotDesktopHome.png" alt="Home" height="350"/>
</p>

#### Connected People
<p align="center">
	<img src="screenshots/screenshotAndroidPeopleConnected.jpg" alt="Connected People" height="350"/>
	<img src="screenshots/screenshotDesktopPeopleConnected.png" alt="Connected People" height="350"/>
</p>

#### Exam
<p align="center">
	<img src="screenshots/screenshotAndroidQuestion.jpg" alt="Exam" height="350"/>
	<img src="screenshots/screenshotDesktopQuestion.png" alt="Exam" height="350"/>
</p>

#### Score
<p align="center">
	<img src="screenshots/screenshotAndroidPeopleScore.jpg" alt="Score" height="350"/>
	<img src="screenshots/screenshotDesktopPeopleScore.png" alt="Score" height="350"/>
</p>

## Application Deployment
### Supported Platforms
| Android | iOS | JVM (Desktop) |
|---------|-----|---------------|
|   ✅    |  ❓  |      ✅       |

> [!WARNING]
> It has **not been tested on iOS systems**, so it may contain *bugs*.

### Prerequisites
#### Google Gemini
You must obtain an **API Key** from **Google Gemini** in order to use the *AI-powered exam generation* feature.  
You can create one [here](https://aistudio.google.com/api-keys).

#### Development Tools
* [JDK](https://www.oracle.com/java/technologies/downloads/) *(Version used: **21.0.9**)*
* [Gradle](https://gradle.org/install/) *(Version used: **9.2.0**)*
* [Android Studio](https://developer.android.com/studio)
	* **Android >=** 8
	* **Android SDK Build-Tools**
	* **CMake**

> [!IMPORTANT]
> You must have the environment variable `ANDROID_HOME` correctly configured and pointing to your **Android SDK**.

### Project Setup
1. Clone the repository
```sh
git clone https://github.com/Ecco-Charlie/ColaboraStudy.git
```

2. Enter the project directory

```sh
cd ./ColaboraStudy
```

3. Modify the `locale.properties` file according to your preferences

##### Linux || macOS

```sh
$EDITOR locale.properties
```

##### Windows

```sh
notepad locale.properties
```

##### `locale.properties` File

```properties
## If not defined, a warning will be shown when trying to create an exam
GEMINI_API_KEY=xxx-xxxx-xxxx-xxxx

## Optional, defaults to the 'gemini-2.0-flash' model
# GEMINI_MODEL=

## Instead of using AI, local stored questions will be used to test the application
## Default is false
# TEST_MODE= true | false
```

4. Build the application

```sh
gradle build
```

### Create Installer for Each Platform

#### Desktop (Windows | Linux | macOS)

##### Native installer

```sh
gradle packageDistributionForCurrentOS
```

##### JAR file

```sh
gradle packageUberJarForCurrentOS
```

#### Android

```sh
gradle :composeApp:assembleRelease
```

On each platform, the output message should look similar to this:

```sh
Reusing configuration cache.

> Task :composeApp:packageDeb
The distribution is written to /home/rabano/Projects/ColaboraStudy/composeApp/build/compose/binaries/main/deb/soft.exe.colabora.study_1.0.0_amd64.deb

BUILD SUCCESSFUL in 22s
17 actionable tasks: 14 executed, 2 from cache, 1 up-to-date
Configuration cache entry reused.
```

This output provides the *path* to the generated file.

## Technical Specifications

The application was built using [Compose Multiplatform](https://www.jetbrains.com/compose-multiplatform/), allowing the same project to be used across multiple platforms without writing platform-specific code.

### Connections

![Workflow](screenshots/flow.png)

### External Libraries Used

| #  | Library | Description | Dependencies |
| -- | ------- | ----------- | ------------ |
| 1  | **[Koin](https://insert-koin.io/)**| Dependency injection for Kotlin, simple and annotation-free.| `io.insert-koin:koin-compose:4.1.1`<br>`io.insert-koin:koin-compose-viewmodel:4.1.1`|
| 2  | **[Navigation Compose](https://developer.android.com/develop/ui/compose/navigation)**| Screen navigation handling.| `org.jetbrains.androidx.navigation:navigation-compose:2.9.1`|
| 3  | **[Multiplatform Settings (No Args)](https://github.com/russhwolf/multiplatform-settings?tab=readme-ov-file#no-arg-module)** | Key-value storage in Kotlin Multiplatform with no configuration required. | `com.russhwolf:multiplatform-settings-no-arg:1.3.0`|
| 4  | **[Kotlin Serialization JSON](https://kotlinlang.org/docs/serialization.html#example-json-serialization)**| Native serialization and deserialization of Kotlin objects to JSON.| `org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0`|
| 5  | **[FileKit](https://github.com/vinceglb/FileKit)**| Simple and unified file and directory handling in KMP.| `io.github.vinceglb:filekit-dialogs-compose:0.12.0`|
| 6  | **[Krop](https://github.com/StrixG/krop)**| Image cropping for Android and Compose.| `com.attafitamim.krop:ui:0.3.0-alpha01`<br>`com.attafitamim.krop:extensions-filekit:0.3.0-alpha01`|
| 7  | **[Datetime Wheel Picker](https://github.com/darkokoa/compose-datetime-wheel-picker)**| Wheel-style date and time picker.| `org.jetbrains.kotlinx:kotlinx-datetime:0.7.1`<br>`io.github.darkokoa:datetime-wheel-picker:1.1.0-alpha05-compose1.9` |
| 8  | **[Ktor Network (Sockets)](https://ktor.io/docs/server-sockets.html)**| Low-level TCP/UDP sockets using Ktor.| `io.ktor:ktor-network:3.3.3`|
| 9  | **[Markdown Render](https://github.com/mikepenz/multiplatform-markdown-renderer)**| Markdown rendering in Kotlin Multiplatform.| `com.mikepenz:multiplatform-markdown-renderer-m3:0.38.1`|
| 10 | **[Google Generative AI SDK](https://github.com/PatilShreyas/generative-ai-kmp/)**| Generative AI SDK (Gemini) for Kotlin Multiplatform.| `dev.shreyaspatil.generativeai:generativeai-google:0.9.0-1.1.0`|

## License
This project is licensed under the [Apache License 2.0](LICENSE)

> By *soft.exe*
