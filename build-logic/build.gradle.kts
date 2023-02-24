plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    compileOnly(libs.gradle.plugin.android)
    compileOnly(libs.gradle.plugin.kotlin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "rickpedia.android.application"
            implementationClass = "AndroidApplicationPlugin"
        }
        register("kotlinLibrary") {
            id = "rickpedia.kotlin.library"
            implementationClass = "KotlinLibraryPlugin"
        }
        register("androidLibrary") {
            id = "rickpedia.android.library"
            implementationClass = "AndroidLibraryPlugin"
        }
        register("androidUiLibrary") {
            id = "rickpedia.android.library.ui"
            implementationClass = "AndroidUiLibraryPlugin"
        }
        register("androidFeatureLibrary") {
            id = "rickpedia.android.feature"
            implementationClass = "AndroidFeaturePlugin"
        }
        register("jacoco") {
            id = "rickpedia.jacoco"
            implementationClass = "JacocoPlugin"
        }
        register("jacocoMergeReports") {
            id = "rickpedia.jacoco.merge.report"
            implementationClass = "JacocoMergeAllReportPlugin"
        }
    }
}
