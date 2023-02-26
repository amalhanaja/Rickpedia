import helper.androidTestImplementation

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("rickpedia.android.library")
    id("rickpedia.android.hilt")
    alias(libs.plugins.kotlin.ksp)
}

android {
    namespace = "dev.amalhanaja.rickpedia.core.database"
    ksp {
        arg("room.schemaLocation", "$projectDir/schemas")
    }
}

dependencies {
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.room.paging3)
    ksp(libs.room.compiler)

    androidTestImplementation(libs.coroutine.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.kotlin.test)
    androidTestImplementation(libs.androidx.espresso.core)
}
