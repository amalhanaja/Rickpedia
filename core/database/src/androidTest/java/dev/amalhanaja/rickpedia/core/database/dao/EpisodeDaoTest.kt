package dev.amalhanaja.rickpedia.core.database.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import dev.amalhanaja.rickpedia.core.database.RickpediaDatabase
import dev.amalhanaja.rickpedia.core.database.entity.EpisodeEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class EpisodeDaoTest {

    private lateinit var database: RickpediaDatabase
    private lateinit var episodeDao: EpisodeDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, RickpediaDatabase::class.java).build()
        episodeDao = database.episodeDao()
    }

    @Test
    fun whenEpisodesNotFound_thenReturnEmptyList() = runTest {
        // When
        val result = episodeDao.getEpisodes(listOf(1, 2))

        // Then
        assertEquals(emptyList(), result.first())
    }

    @Test
    fun whenInsertEpisodes_thenAddedToDatabase() = runTest {
        // When
        episodeDao.insertEpisodes(listOf(buildEpisode(2)))

        // Then
        assertEquals(listOf(buildEpisode(2)), episodeDao.getEpisodes(listOf(2)).first())
    }

    private fun buildEpisode(id: Int): EpisodeEntity {
        return EpisodeEntity(
            id = id,
            name = "Name of $id",
            code = "Code of $id",
            airDate = "Airdate of $id",
        )
    }
}
