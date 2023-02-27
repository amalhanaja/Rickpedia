package dev.amalhanaja.rickpedia.core.data.repository

import dev.amalhanaja.rickpedia.core.model.Episode
import kotlinx.coroutines.flow.Flow

interface EpisodeRepository {

    fun getEpisodes(episodes: List<Int>): Flow<List<Episode>>
}
