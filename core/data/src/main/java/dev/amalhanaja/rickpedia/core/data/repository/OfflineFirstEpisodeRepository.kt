package dev.amalhanaja.rickpedia.core.data.repository

import dev.amalhanaja.rickpedia.core.data.mapper.toEpisode
import dev.amalhanaja.rickpedia.core.data.mapper.toEpisodeEntity
import dev.amalhanaja.rickpedia.core.database.dao.EpisodeDao
import dev.amalhanaja.rickpedia.core.model.Episode
import dev.amalhanaja.rickpedia.core.network.RickpediaNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OfflineFirstEpisodeRepository @Inject constructor(
    private val episodeDao: EpisodeDao,
    private val networkDataSource: RickpediaNetworkDataSource,
) : EpisodeRepository {

    override fun getEpisodes(episodes: List<Int>): Flow<List<Episode>> = episodeDao.getEpisodes(episodes)
        .map { list -> list.map { it.toEpisode() } }
        .onEach { list -> if (list.count() < episodes.count()) getFromNetworkAndCache(episodes) }

    private suspend fun getFromNetworkAndCache(episodes: List<Int>) {
        val response = networkDataSource.getEpisodes(episodes)
        val entities = response.map { it.toEpisode().toEpisodeEntity() }
        episodeDao.insertEpisodes(entities)
    }

}
