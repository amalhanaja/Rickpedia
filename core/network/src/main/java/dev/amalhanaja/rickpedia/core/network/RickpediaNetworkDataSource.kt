package dev.amalhanaja.rickpedia.core.network

import dev.amalhanaja.rickpedia.core.network.response.CharacterResponse
import dev.amalhanaja.rickpedia.core.network.response.EpisodeResponse

interface RickpediaNetworkDataSource {
    suspend fun getAllCharacter(page: Int): List<CharacterResponse>
    suspend fun getEpisodes(episodes: List<Int>): List<EpisodeResponse>
}
