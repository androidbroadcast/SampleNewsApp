import dev.iurysouza.modulegraph.Orientation
import io.gitlab.arturbosch.detekt.extensions.DetektExtension

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.jetbrainsKotlinJvm) apply false
    alias(libs.plugins.kotlinSerialization) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.androidx.room) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.android.test) apply false
    alias(libs.plugins.baselineprofile) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.jetbrainsCompose) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    id("dev.iurysouza.modulegraph") version "0.10.1"
}

// TODO Replace Detekt Gradle with terminal launch
allprojects.onEach { project ->
    project.afterEvaluate {
        with(project.plugins) {
            if (hasPlugin(libs.plugins.jetbrainsKotlinAndroid.get().pluginId) ||
                hasPlugin(libs.plugins.jetbrainsKotlinJvm.get().pluginId)
            ) {
                apply(libs.plugins.detekt.get().pluginId)

                project.extensions.configure<DetektExtension> {
                    config.setFrom(rootProject.files("default-detekt-config.yml"))
                }

                project.dependencies.add(
                    "detektPlugins",
                    libs.detekt.formatting.get().toString()
                )
            }

            if (hasPlugin(
                    libs.plugins.compose.compiler
                        .get()
                        .pluginId
                )
            ) {
                project.dependencies.add(
                    "detektPlugins",
                    libs.detekt.rules.compose
                        .get()
                        .toString()
                )
            }
        }
    }
}

moduleGraphConfig {
    readmePath.set("./README.md")
    heading = "### Module Graph"
    // showFullPath.set(false) // optional
     orientation.set(Orientation.RIGHT_TO_LEFT) //optional
    // linkText.set(LinkText.NONE) // optional
    // setStyleByModuleType.set(true) // optional
    // excludedConfigurationsRegex.set(".*test.*") // optional
    // excludedModulesRegex.set(".*moduleName.*") // optional
    // focusedModulesRegex.set(".*(projectName).*") // optional
     rootModulesRegex.set(".*app.*") // optional
    // theme.set(Theme.NEUTRAL) // optional
    // or you can fully customize it by using the BASE theme:
    // Theme.BASE(
    //     themeVariables = mapOf(
    //         "primaryTextColor" to "#F6F8FAff", // Text
    //         "primaryColor" to "#5a4f7c", // Node
    //         "primaryBorderColor" to "#5a4f7c", // Node border
    //         "tertiaryColor" to "#40375c", // Container box background
    //         "lineColor" to "#f5a623",
    //         "fontSize" to "12px",
    //     ),
    //     focusColor = "#F5A622",
    //     moduleTypes = listOf(
    //         ModuleType.AndroidLibrary("#2C4162"),
    //     )
    // ),
    // )

    // You can add additional graphs.
    // A separate graph will be generated for each config below.
    // graph(
    //     readmePath = "./README.md",
    //     heading = "# Graph with root: gama",
    // ) {
    //     rootModulesRegex = ".*gama.*"
    // }
    // graph(
    //     readmePath = "./SomeOtherReadme.md",
    //     heading = "# Graph",
    // ) {
    //     rootModulesRegex = ".*zeta.*"
    // }
    focusedModulesRegex.set(".*(compose-app).*")
}

