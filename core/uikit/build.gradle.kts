@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.detekt)
    id("dev.androidbroadcast.news.kmpplugin.compose")
}

kotlin {
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
    compileSdk = libs.versions.androidSdk.compile.get().toInt()

    defaultConfig {
        minSdk = libs.versions.androidSdk.min.get().toInt()
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
