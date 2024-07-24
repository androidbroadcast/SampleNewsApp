plugins {
    `kotlin-dsl`
}

group = "dev.androidbroadcast.news.buildlogic"

// Configure the build-logic plugins to target JDK 17
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    jvmToolchain {
        version = JavaVersion.VERSION_21
    }

    compilerOptions {
        target {
            version = JavaVersion.VERSION_17
        }
    }
}

dependencies {
    compileOnly(libs.gradlePlugins.android)
    compileOnly(libs.gradlePlugins.kotlin)
}

gradlePlugin {
    plugins {
        register("newsKmpPlugin") {
            id = "dev.androidbroadcast.news.kmpplugin"
            implementationClass = "dev.androidbroadcast.news.buildlogic.NewsKMPPlugin"
        }

        register("newsKmpPluginCompose") {
            id = "dev.androidbroadcast.news.kmpplugin.compose"
            implementationClass = "dev.androidbroadcast.news.buildlogic.NewsKMPComposePlugin"
        }
    }
}
