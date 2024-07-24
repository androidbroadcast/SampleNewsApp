package dev.androidbroadcast.news.buildlogic

import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class NewsKMPPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.multiplatform")
                apply("com.android.library")
            }

            val newsKMPExtension = extensions.create<NewsKMPExtension>("newsProject")

            extensions.configure<LibraryExtension> {
                val androidLibraryExt: LibraryExtension = this
                extensions.configure<KotlinMultiplatformExtension> {
                    setupKMPModule(this, androidLibraryExt, newsKMPExtension, withCompose = true)
                }
            }
        }
    }
}
