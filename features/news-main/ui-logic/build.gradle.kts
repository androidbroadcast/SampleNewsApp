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
//    explicitApi = ExplicitApiMode.Strict

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
            baseName = "FeaturesNewsMainUiLogic"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            api(projects.core.data)
            api(projects.core.common)
            api(projects.core.database)
            api(projects.core.opennewsApi)
            implementation(libs.androidx.lifecycle.runtime)
            implementation(libs.androidx.lifecycle.viewmodel)

            implementation(projects.core.platform)
        }

        androidMain.dependencies {
            implementation(libs.androidx.core.ktx)
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

dependencies {
    "kspAndroid"(libs.kotlinInject.compiler)
    "kspIosArm64"(libs.kotlinInject.compiler)
    "kspIosSimulatorArm64"(libs.kotlinInject.compiler)
    "kspJvm"(libs.kotlinInject.compiler)
}
