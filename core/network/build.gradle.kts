@file:Suppress("UnstableApiUsage")

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("rickpedia.android.library")
    id("rickpedia.android.hilt")
    alias(libs.plugins.secrets)
}

android {
    namespace = "dev.amalhanaja.rickpedia.core.network"

    buildFeatures {
        buildConfig = true
    }
}

secrets {
    defaultPropertiesFileName = "secrets.defaults.properties"
}

dependencies {
    implementation(libs.gson)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp.logging)

    testImplementation(libs.okhttp.mock)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.junit)
    testImplementation(libs.coroutine.test)
    testImplementation(libs.mockk)
}
