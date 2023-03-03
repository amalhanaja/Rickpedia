package dev.amalhanaja.rickpedia.feature.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.amalhanaja.rickpedia.core.data.repository.EpisodeRepository
import dev.amalhanaja.rickpedia.core.model.Episode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val episodeRepository: EpisodeRepository,
) : ViewModel() {

    private val _episodeUiState = MutableStateFlow<EpisodesUiState>(EpisodesUiState.Loading)
    val episodeUiState = _episodeUiState.asStateFlow()

    fun fetchEpisodes(episodeIds: List<Int>) {
        episodeRepository.getEpisodes(episodeIds)
            .map<List<Episode>, EpisodesUiState> { EpisodesUiState.WithData(it) }
            .catch { emit(EpisodesUiState.Error(it)) }
            .onStart { emit(EpisodesUiState.Loading) }
            .onEach { _episodeUiState.emit(it) }
            .launchIn(viewModelScope)
    }
}
