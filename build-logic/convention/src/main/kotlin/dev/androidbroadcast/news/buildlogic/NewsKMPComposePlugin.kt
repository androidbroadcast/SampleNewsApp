package dev.androidbroadcast.news.buildlogic

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class NewsKMPComposePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.multiplatform")
                apply("com.android.library")
            }

            val newsKMPExtension = extensions.create<NewsKMPExtension>("newsProject")
            val androidLibraryExt: LibraryExtension = extensions.getByType()
            val kmpExt: KotlinMultiplatformExtension = extensions.getByType()

            setupKMPModule(
                kmpExt,
                androidLibraryExt,
                newsKMPExtension,
                withCompose = true
            )
        }
    }
}
