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
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.kapt)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_1_8)
        }
    }

    sourceSets  {
        commonMain.dependencies {
            implementation(libs.kotlinx.coroutines.core)
            implementation(projects.features.newsMain.uiLogic)
            implementation(projects.core.common)
            implementation(libs.coil.compose)
            implementation(projects.core.uikit)
        }

        androidMain.dependencies {
            implementation(libs.androidx.core.ktx)
            implementation(libs.androidx.lifecycle.runtime.ktx)
            implementation(libs.androidx.activity.compose)

            implementation(libs.androidx.lifecycle.viewmodel.ktx)
            implementation(libs.androidx.lifecycle.viewmodel.compose)

            implementation(libs.kotlinx.coroutines.android)

            implementation(libs.dagger.hilt.android)

            implementation(project.dependencies.platform(libs.androidx.compose.bom))
        }
    }
}

android {
    namespace = "dev.androidbroadcast.news.main.ui"
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

dependencies {
    "kapt"(libs.dagger.hilt.compiler)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.tooling.preview)
}
