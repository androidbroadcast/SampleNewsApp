plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.detekt)
}

kotlin {
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "NewsShared"
            isStatic = true
        }
    }

    sourceSets  {
        commonMain.dependencies {
            implementation(projects.composeApp)
            implementation(projects.core.platform)
        }
    }
}
