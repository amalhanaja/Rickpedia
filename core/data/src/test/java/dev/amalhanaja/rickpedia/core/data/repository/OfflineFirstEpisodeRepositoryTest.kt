package dev.amalhanaja.rickpedia.core.data.repository

import dev.amalhanaja.rickpedia.core.data.mapper.toEpisode
import dev.amalhanaja.rickpedia.core.data.mapper.toEpisodeEntity
import dev.amalhanaja.rickpedia.core.database.dao.EpisodeDao
import dev.amalhanaja.rickpedia.core.database.entity.EpisodeEntity
import dev.amalhanaja.rickpedia.core.network.RickpediaNetworkDataSource
import dev.amalhanaja.rickpedia.core.network.response.EpisodeResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class OfflineFirstEpisodeRepositoryTest {

    private lateinit var subject: OfflineFirstEpisodeRepository
    private lateinit var episodeEntitiesFlow: Flow<List<EpisodeEntity>>
    private val episodeDao = mockk<EpisodeDao>()
    private val networkDataSource = mockk<RickpediaNetworkDataSource>()

    @Before
    fun setUp() {
        val mutableStateEpisodes = MutableStateFlow<List<EpisodeEntity>>(emptyList())
        val insertedEpisodeSlot = slot<List<EpisodeEntity>>()
        episodeEntitiesFlow = mutableStateEpisodes
        every { episodeDao.getEpisodes(any()) } returns episodeEntitiesFlow
        coEvery { episodeDao.insertEpisodes(capture(insertedEpisodeSlot)) } coAnswers { mutableStateEpisodes.emit(insertedEpisodeSlot.captured) }
        subject = OfflineFirstEpisodeRepository(episodeDao, networkDataSource)
    }

    @Test
    fun givenEmptyDatabase_whenGetEpisodes_thenFetchFromNetwork() = runTest {
        // Given
        coEvery { networkDataSource.getEpisodes(listOf(1, 2, 3)) } returns listOf(buildEpisodeResponse())

        // When
        subject.getEpisodes(listOf(1, 2, 3)).first()

        // Then
        coVerify { networkDataSource.getEpisodes(listOf(1, 2, 3)) }
        coVerify { episodeDao.insertEpisodes(listOf(buildEpisodeResponse().toEpisode().toEpisodeEntity())) }
    }

    @Test
    fun givenSameDataCount_whenGetEpisodes_thenReturnValuesWithoutFetchFromNetwork() = runTest {
        // Given
        episodeDao.insertEpisodes(listOf(buildEpisodeResponse().toEpisode().toEpisodeEntity()))

        // When
        val result = subject.getEpisodes(listOf(2)).first()

        // Then
        assertEquals(1, result.count())
        assertEquals(2, result.first().id)
    }

    private fun buildEpisodeResponse(): EpisodeResponse {
        return EpisodeResponse(
            id = 2,
            name = "Lawnmower Dog",
            airDate = "December 9, 2013",
            episode = "S01E02",
            characters = listOf("https://rickandmortyapi.com/api/character/1"),
            url = "https://rickandmortyapi.com/api/episode/2",
            created = "2017-11-10T12:56:33.916Z",
        )
    }

}
