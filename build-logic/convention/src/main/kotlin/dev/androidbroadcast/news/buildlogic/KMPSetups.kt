package dev.androidbroadcast.news.buildlogic

import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.setupKMPModule(
    kmpExt: KotlinMultiplatformExtension,
    androidLibraryExt: LibraryExtension,
    newsKMPExtension: NewsKMPExtension,
    withCompose: Boolean,
) {
    kmpExt.apply {
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
                baseName = this@setupKMPModule.displayName
                isStatic = true
            }
        }

        sourceSets.apply {
            commonMain.dependencies {
                api(versionCatalog.findLibrary("kotlinx-coroutines-core").get())
                api(versionCatalog.findLibrary("kotlinx-immutable").get())
                api(versionCatalog.findLibrary("kotlinx-datetime").get())
                api(versionCatalog.findLibrary("koin-core").get())
            }

            androidMain.dependencies {
                api(versionCatalog.findLibrary("androidx-core-ktx").get())
                api(versionCatalog.findLibrary("kotlinx-coroutines-android").get())
                api(versionCatalog.findLibrary("koin-android").get())
            }

            jvmMain.dependencies {
                api(versionCatalog.findLibrary("kotlinx-coroutines-swing").get())
            }
        }
    }

    androidLibraryExt.apply {
        namespace = newsKMPExtension.androidNamespace.get()
        compileSdk =
            versionCatalog
                .findVersion("androidSdk-compile")
                .get()
                .toString()
                .toInt()

        defaultConfig {
            minSdk =
                versionCatalog
                    .findVersion("androidSdk-min")
                    .get()
                    .toString()
                    .toInt()
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
    }

    if (withCompose) {
        setupComposeMp(kmpExt, androidLibraryExt)
    }
}

private fun Project.setupComposeMp(
    kmpExt: KotlinMultiplatformExtension,
    androidLibraryExt: LibraryExtension,
) {
    with(pluginManager) {
        apply("org.jetbrains.kotlin.plugin.compose")
        apply("org.jetbrains.compose")
    }

    androidLibraryExt.apply {
        buildFeatures.apply {
            compose = true
        }

        dependencies {
            add("debugImplementation", versionCatalog.findLibrary("compose-uiTooling").get())
        }
    }
}
