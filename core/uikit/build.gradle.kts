@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.detekt)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_1_8)
        }
    }

    jvm()

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "CoreUiKit"
            isStatic = true
        }
    }

    sourceSets  {
        commonMain.dependencies {
            api(compose.ui)
            api(compose.runtime)
            api(compose.foundation)
            api(compose.material3)
            api(compose.components.resources)
            api(compose.components.uiToolingPreview)
        }

        androidMain.dependencies {
            implementation(compose.preview)

            implementation(libs.androidx.core.ktx)
            // Need only for debug
            implementation(libs.androidx.compose.ui.tooling)
            // Need only for debug
            implementation(libs.androidx.compose.ui.test.manifest)
            implementation(project.dependencies.platform(libs.androidx.compose.bom))
        }
    }
}

android {
    namespace = "dev.androidbroadcast.news.uikit"
    compileSdk = libs.versions.androidSdk.min.get().toInt()

    defaultConfig {
        compileSdk = libs.versions.androidSdk.compile.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        compose = true
    }

    dependencies {
        debugImplementation(compose.uiTooling)
    }
}
