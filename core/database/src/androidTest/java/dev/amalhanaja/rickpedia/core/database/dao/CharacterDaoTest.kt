package dev.amalhanaja.rickpedia.core.database.dao

import android.content.Context
import androidx.paging.PagingSource
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import dev.amalhanaja.rickpedia.core.database.RickpediaDatabase
import dev.amalhanaja.rickpedia.core.database.entity.CharacterEntity
import dev.amalhanaja.rickpedia.core.database.entity.CharacterEpisodeCrossRefEntity
import dev.amalhanaja.rickpedia.core.database.entity.CharacterWithEpisodeIdsEntity
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class CharacterDaoTest {

    private lateinit var subject: CharacterDao
    private lateinit var database: RickpediaDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, RickpediaDatabase::class.java).build()
        subject = database.characterDao()
    }

    @Test
    fun whenGetAllCharacterAndDataIsEmpty_thenReturnEmpty() = runTest {
        // When
        val result = subject.getAllCharacter().load(PagingSource.LoadParams.Refresh(null, 20, false))

        // Then
        assertEquals(
            PagingSource.LoadResult.Page<Int, CharacterWithEpisodeIdsEntity>(emptyList(), null, null, 0, 0),
            result,
        )
    }

    @Test
    fun whenInsertCharacters_thenAddDataToDatabase() = runTest {
        // When
        subject.insertCharacters(listOf(buildCharacter(1), buildCharacter(5)))

        // Then
        val storedData = subject.getAllCharacter().load(PagingSource.LoadParams.Refresh(null, 20, false))
        assertIs<PagingSource.LoadResult.Page<Int, CharacterWithEpisodeIdsEntity>>(storedData)
        assertEquals(2, storedData.data.count())
        assertEquals(
            listOf(
                CharacterWithEpisodeIdsEntity(buildCharacter(1), emptyList()),
                CharacterWithEpisodeIdsEntity(buildCharacter(5), emptyList()),
            ),
            storedData.data,
        )
    }

    @Test
    fun whenInsertCharacterWithEpisodeCrossRefs_thenAddToDatabase() = runTest {
        // When
        subject.insertCharacters(listOf(buildCharacter(1)))
        subject.insertCharacterEpisodeCrossRefs(
            listOf(
                CharacterEpisodeCrossRefEntity(1, 1),
                CharacterEpisodeCrossRefEntity(1, 2),
            ),
        )

        // Then
        val storedData = subject.getAllCharacter().load(PagingSource.LoadParams.Refresh(null, 20, false))
        assertIs<PagingSource.LoadResult.Page<Int, CharacterWithEpisodeIdsEntity>>(storedData)
        assertEquals(1, storedData.data.count())
        assertEquals(buildCharacter(1), storedData.data.first().character)
        assertEquals(
            listOf(
                CharacterEpisodeCrossRefEntity(1, 1),
                CharacterEpisodeCrossRefEntity(1, 2),
            ),
            storedData.data.first().characterEpisodeCrossRefEntity,
        )
    }

    private fun buildCharacter(id: Int) = CharacterEntity(
        id = id,
        name = "Name of $id",
        status = "status of $id",
        species = "species of $id",
        subSpecies = "sub species of $id",
        gender = "gender of $id",
        origin = "origin of $id",
        location = "location of $id",
        image = "image of $id",
    )
}
