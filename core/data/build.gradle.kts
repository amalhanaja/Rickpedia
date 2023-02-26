plugins {
    id("rickpedia.android.library")
    id("rickpedia.android.hilt")
}

android {
    namespace = "dev.amalhanaja.rickpedia.core.data"
}

dependencies {
    implementation(project(":core:datastore"))
    implementation(project(":core:database"))
    implementation(project(":core:network"))
    implementation(project(":core:model"))
    implementation(libs.paging3.runtime)
}
