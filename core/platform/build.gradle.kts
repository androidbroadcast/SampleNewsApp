@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.ksp)
}

kotlin {
    explicitApi = ExplicitApiMode.Strict

    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_1_8)
        }
    }

    jvm()

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "CorePlatform"
            isStatic = true
        }
    }

    sourceSets  {
        commonMain.dependencies {
            api(projects.core.common)
            implementation(libs.coil.compose)
            implementation(projects.core.data)
        }

        jvmMain.dependencies {
            implementation(libs.androidx.sqlite.bundled)
        }

        iosMain.dependencies {
            implementation(libs.androidx.sqlite.bundled)
        }
    }
}

android {
    namespace = "dev.androidbroadcast.news.core.platform"
    compileSdk = libs.versions.androidSdk.compile.get().toInt()

    defaultConfig {
        minSdk = libs.versions.androidSdk.min.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    "kspAndroid"(libs.kotlinInject.compiler)
    "kspIosArm64"(libs.kotlinInject.compiler)
    "kspIosSimulatorArm64"(libs.kotlinInject.compiler)
    "kspJvm"(libs.kotlinInject.compiler)
}
