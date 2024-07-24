package dev.androidbroadcast.news.buildlogic

import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.kotlin.dsl.property

open class NewsKMPExtension internal constructor(project: Project) {

    var androidNamespace: Property<String> = project.objects.property<String>().convention("")
}
