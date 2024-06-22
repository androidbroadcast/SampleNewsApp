@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
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
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "FeaturesNewsMainUiLogic"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.core.data)
            api(projects.core.common)
            implementation(libs.androidx.lifecycle.runtime)
            implementation(libs.androidx.lifecycle.viewmodel)
            api(libs.koin.compose.viewmodel)
        }

        androidMain.dependencies {
            implementation(libs.androidx.core.ktx)
            api(libs.koin.androidx.compose)
            api(libs.koin.android)
        }
    }
}

android {
    namespace = "dev.androidbroadcast.news.main.uilogic"
    compileSdk = libs.versions.androidSdk.compile.get().toInt()

    defaultConfig {
        minSdk = libs.versions.androidSdk.min.get().toInt()
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

configurations.all {
    resolutionStrategy.eachDependency {
        if (requested.name.startsWith("lifecycle-runtime-ktx")) {
            useTarget("androidx.lifecycle:${requested.name.replace("lifecycle-runtime-ktx", "lifecycle-runtime")}:${requested.version}")
        } else if (requested.name.startsWith("lifecycle-viewmodel-ktx")) {
            useTarget("androidx.lifecycle:${requested.name.replace("lifecycle-viewmodel-ktx", "lifecycle-runtime")}:${requested.version}")
        }
    }
}
