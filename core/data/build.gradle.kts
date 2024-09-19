import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

kotlin {
    explicitApi = ExplicitApiMode.Strict
    jvmToolchain(21)
}

android {
    namespace = "dev.androidbroadcast.news.data"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    kotlinOptions {
        jvmTarget = "21"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.android)

    implementation(projects.core.database)
    implementation(projects.core.opennewsApi)
    implementation(projects.core.common)

    implementation(libs.javax.inject)
}
