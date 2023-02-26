@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("rickpedia.android.library")
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
}
