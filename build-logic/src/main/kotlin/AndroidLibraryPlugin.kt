import configurator.configureAndroid
import configurator.configureKotlin
import helper.applyPlugins
import helper.findCommonExtension
import helper.getLibraryProvider
import helper.getPluginId
import helper.implementation
import helper.libsVersionCatalog
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.applyPlugins(
            libsVersionCatalog.getPluginId("android.library"),
            libsVersionCatalog.getPluginId("kotlin.android"),
            "rickpedia.jacoco",
            "rickpedia.ktlint",
        )
        findCommonExtension()?.apply {
            configureAndroid(this)
        }
        configureKotlin()

        dependencies {
            implementation(libsVersionCatalog.getLibraryProvider("androidx.core"))
        }
    }
}
