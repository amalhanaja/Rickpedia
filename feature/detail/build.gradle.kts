plugins {
    id("rickpedia.android.feature")
    id("rickpedia.android.hilt")
}

android {
    namespace = "dev.amalhanaja.rickpedia.feature.detail"
}

dependencies {
    implementation(libs.paging3.compose)
    implementation(libs.coil.compose)
    implementation(libs.compose.constraint.layout)
    implementation(libs.accompanist.navigation.animation)
}
