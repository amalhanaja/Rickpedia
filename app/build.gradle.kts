@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("rickpedia.android.application")
    id("rickpedia.android.hilt")
}

fun generateVersionCode(minSdk: Int, major: Int, minor: Int, patch: Int): Int {
    return (minSdk * 10_000_000) + (major * 10_000) + (minor * 100) + patch
}

fun generateVersionName(major: Int, minor: Int, patch: Int, versionIdentifier: String? = null, buildNumber: Long? = null): String {
    val builder = StringBuilder("$major.$minor.$patch")
    versionIdentifier?.let { builder.append("-$it") }
    buildNumber?.let { builder.append("+$it") }
    return builder.toString()
}

@Suppress("UnstableApiUsage")
android {
    namespace = "dev.amalhanaja.rickpedia"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        val major = apps.versions.major.get().toInt()
        val minor = apps.versions.minor.get().toInt()
        val patch = apps.versions.patch.get().toInt()
        applicationId = "dev.amalhanaja.rickpedia"
        versionCode = generateVersionCode(libs.versions.minSdk.get().toInt(), major, minor, patch)
        versionName = generateVersionName(
            major = major,
            minor = minor,
            patch = patch,
            buildNumber = TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis()),
        )

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {

    implementation(project(":feature:home"))
    implementation(project(":core:designsystem"))
    implementation(libs.androidx.core)
    implementation(libs.lifecycle.compose)
    implementation(libs.compose.activity)
    implementation(libs.compose.ui)
    implementation(libs.compose.preview)
    implementation(libs.compose.material3)
    implementation(libs.accompanist.navigation.animation)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.compose.test.junit)
    debugImplementation(libs.compose.tooling)
    debugImplementation(libs.compose.test.manifest)
}
