package dev.amalhanaja.rickpedia.feature.detail

import dev.amalhanaja.rickpedia.core.model.Episode

sealed interface EpisodesUiState {
    object Loading : EpisodesUiState
    data class Error(val throwable: Throwable) : EpisodesUiState
    data class WithData(val data: List<Episode>) : EpisodesUiState
}
