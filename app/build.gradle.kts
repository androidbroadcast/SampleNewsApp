plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.kapt)
    alias(libs.plugins.baselineprofile)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "dev.androidbroadcast.newssearchapp"
    compileSdk = libs.versions.androidSdk.compile.get().toInt()

    defaultConfig {
        applicationId = "dev.androidbroadcast.newssearchapp"
        minSdk = libs.versions.androidSdk.min.get().toInt()
        targetSdk = libs.versions.androidSdk.target.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("String", "NEWS_API_KEY", "\"155ae65d7264461397c901103488c01e\"")
        buildConfigField("String", "NEWS_API_BASE_URL", "\"https://newsapi.org/v2/\"")

        resourceConfigurations += setOf("ru", "en")
        ndk {
            //noinspection ChromeOsAbiSupport
            abiFilters += setOf("armeabi-v7a", "arm64-v8a")
        }
    }

    signingConfigs {
        create("release") {
            storeFile = File(rootDir, "newsapp.keystore")
            // Password must be store somewhere in private storage
            keyPassword = "12345678"
            keyAlias = "k.rozov"
            storePassword = "12345678"
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs["release"]
            isMinifyEnabled = false
            proguardFile(getDefaultProguardFile("proguard-android-optimize.txt"),)
            proguardFile(file("proguard/"))
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/okhttp3/internal/publicsuffix/NOTICE"
            excludes += "/kotlin/**"
            excludes += "META-INF/androidx.*.version"
            excludes += "META-INF/com.google.*.version"
            excludes += "META-INF/kotlinx_*.version"
            excludes += "kotlin-tooling-metadata.json"
            excludes += "DebugProbesKt.bin"
            excludes += "META-INF/com/android/build/gradle/*"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.dagger.hilt.android)
    implementation(libs.androidx.profileinstaller)
    kapt(libs.dagger.hilt.compiler)

    implementation(projects.core.data)
    implementation(projects.core.opennewsApi)
    implementation(projects.features.newsMain.ui)
    implementation(projects.core.database)
    implementation(projects.core.common)
    implementation(projects.core.uikit)

    debugImplementation(libs.okhttp.logging.interceptor)

    baselineProfile(projects.baselineprofile)
}
