@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.detekt)
}

kotlin {
    explicitApi = ExplicitApiMode.Strict

    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_1_8)
        }
    }

    sourceSets  {
        commonMain.dependencies {
            implementation(projects.features.newsMain.ui)
            implementation(projects.core.database)
            implementation(projects.core.common)
            implementation(projects.core.uikit)
            implementation(projects.core.data)
            implementation(projects.core.opennewsApi)

            implementation(libs.koin.core)
            implementation(libs.koin.compose)

            implementation(libs.coil.core)
            implementation(libs.ktor.client.core)

            implementation(libs.androidx.room.ktx)
        }

        androidMain.dependencies {
            implementation(libs.koin.android)
        }
    }
}

android {
    namespace = "dev.androidbroadcast.news.compose"
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
}

composeCompiler {
    enableStrongSkippingMode = true
    reportsDestination = layout.buildDirectory.dir("compose_compiler")
}
