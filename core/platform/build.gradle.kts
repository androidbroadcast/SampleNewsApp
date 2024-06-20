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

    sourceSets  {
        commonMain.dependencies {
            api(projects.core.common)
            implementation(libs.coil.compose)
            implementation(projects.core.data)
            implementation(projects.core.database)
            implementation(projects.core.opennewsApi)
        }

        jvmMain.dependencies {
            implementation(libs.androidx.sqlite.bundled)
        }
    }
}

android {
    namespace = "dev.androidbroadcast.news.core.platform"
    compileSdk = libs.versions.androidSdk.min.get().toInt()

    defaultConfig {
        compileSdk = libs.versions.androidSdk.compile.get().toInt()
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}