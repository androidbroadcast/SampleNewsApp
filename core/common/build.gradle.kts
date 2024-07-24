plugins {
    alias(libs.plugins.detekt)
    id("dev.androidbroadcast.news.kmpplugin")
}

newsProject {
    androidNamespace.set("dev.androidbroadcast.news.common")
}

kotlin {
    sourceSets  {
        commonMain.dependencies {
            api(libs.coil.core)
            implementation(libs.coil.network.ktor)
        }
    }
}
