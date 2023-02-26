plugins {
    id("rickpedia.android.library")
    id("rickpedia.android.hilt")
}

android {
    namespace = "dev.amalhanaja.rickpedia.core.datastore"
}

dependencies {
    implementation(libs.datastore.preferences)

    testImplementation(libs.mockk)
    testImplementation(libs.junit)
    testImplementation(libs.coroutine.test)
    testImplementation(libs.kotlin.test)
}
