package dev.amalhanaja.rickpedia.core.network.api

import dev.amalhanaja.rickpedia.core.network.BuildConfig

internal object RickpediaBaseUrlProvider {
    fun provideBaseUrl(): String {
        return BuildConfig.RICK_AND_MORTY_BASE_URL
    }
}
