import com.codingfeline.buildkonfig.compiler.FieldSpec.Type
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    kotlin("plugin.serialization") version "2.3.0"
    id("com.codingfeline.buildkonfig")
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    jvm()
    
    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.core.splashscreen)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
            implementation(libs.navigation.compose)
            implementation(libs.multiplatform.settings.no.arg)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.filekit.dialogs.compose)
            implementation(libs.krop.ui)
            implementation(libs.krop.extension.filekit)
            implementation(libs.kotlinx.datetime)
            implementation(libs.datetime.wheel.picker)
            implementation(libs.ktor.network)
            implementation(libs.multiplatform.markdown.renderer.m3)
            implementation(libs.generativeai.google)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
        }
    }
}

android {
    namespace = "soft.exe.colabora.study"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "soft.exe.colabora.study"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "soft.exe.colabora.study.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "soft.exe.colabora.study"
            packageVersion = "1.0.0"
            linux {
                iconFile.set(project.file("src/commonMain/composeResources/drawable/icons/logo_bitmap.png"))
            }
            windows {
                iconFile.set(project.file("src/commonMain/composeResources/drawable/icons/logo_bitmap.ico"))
            }
            macOS {
                iconFile.set(project.file("src/commonMain/composeResources/drawable/icons/logo_bitmap.icns"))
            }
        }
    }
}

buildkonfig {
    packageName = "soft.exe.colabora.study"

    val geminiPropertiesFile = rootProject.file("gemini.properties")
    var geminiProperties = Properties()
    if (geminiPropertiesFile.exists())
        geminiProperties.load(geminiPropertiesFile.inputStream())

    val GEMINI_API_KEY: String? = geminiProperties.getProperty("GEMINI_API_KEY")
    val GEMINI_MODEL: String = geminiProperties.getProperty("GEMINI_MODEL") ?: "gemini-2.0-flash"

    defaultConfigs {
        buildConfigField(
            type = Type.STRING,
            name = "GEMINI_API_KEY",
            value = GEMINI_API_KEY,
            nullable = true
        )
        buildConfigField(
            type = Type.STRING,
            name = "GEMINI_MODEL",
            value = GEMINI_MODEL
        )
    }
}