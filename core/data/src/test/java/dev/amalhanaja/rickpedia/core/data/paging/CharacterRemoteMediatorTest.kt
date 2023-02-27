package dev.amalhanaja.rickpedia.core.data.paging

import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator.MediatorResult
import dev.amalhanaja.rickpedia.core.data.mapper.toCharacter
import dev.amalhanaja.rickpedia.core.data.mapper.toCharacterEntity
import dev.amalhanaja.rickpedia.core.database.dao.CharacterDao
import dev.amalhanaja.rickpedia.core.database.entity.CharacterEntity
import dev.amalhanaja.rickpedia.core.database.entity.CharacterEpisodeCrossRefEntity
import dev.amalhanaja.rickpedia.core.datastore.RickpediaPreferenceDataSource
import dev.amalhanaja.rickpedia.core.network.RickpediaNetworkDataSource
import dev.amalhanaja.rickpedia.core.network.response.CharacterResponse
import dev.amalhanaja.rickpedia.core.network.response.LocationResponse
import dev.amalhanaja.rickpedia.core.network.response.OriginResponse
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class CharacterRemoteMediatorTest {

    private val characterDao: CharacterDao = mockk()
    private val networkDataSource: RickpediaNetworkDataSource = mockk()
    private val preferenceDataSource: RickpediaPreferenceDataSource = mockk()

    private val subject = CharacterRemoteMediator(characterDao, networkDataSource, preferenceDataSource)

    @Test
    fun whenLoadRefresh_thenGetFirstPageFromNetworkDataSourceSetAllCharacterNextPageAndInsertToDatabase() = runTest {
        // Given
        val pageSlot = slot<Int>()
        val nextPageSlot = slot<Int>()
        val characterEntitiesSlot = slot<List<CharacterEntity>>()
        val characterEpisodeCrossRefEntities = slot<List<CharacterEpisodeCrossRefEntity>>()
        coEvery { networkDataSource.getAllCharacter(capture(pageSlot)) } returns listOf(buildCharacterResponse(1))
        coEvery { preferenceDataSource.setAllCharacterNextPage(capture(nextPageSlot)) } just Runs
        coEvery { characterDao.insertCharacters(capture(characterEntitiesSlot)) } just Runs
        coEvery { characterDao.insertCharacterEpisodeCrossRefs(capture(characterEpisodeCrossRefEntities)) } just Runs

        // When
        val result = subject.load(
            LoadType.REFRESH,
            PagingState(listOf(), null, PagingConfig(20), 10),
        )

        // Then
        assertEquals(1, pageSlot.captured)
        assertEquals(2, nextPageSlot.captured)
        assertEquals(listOf(buildCharacterResponse(1).toCharacter().toCharacterEntity()), characterEntitiesSlot.captured)
        assertEquals(
            listOf(
                CharacterEpisodeCrossRefEntity(1, 1),
                CharacterEpisodeCrossRefEntity(1, 2),
            ),
            characterEpisodeCrossRefEntities.captured,
        )
        assertIs<MediatorResult.Success>(result)
        assertEquals(false, result.endOfPaginationReached)
    }

    @Test
    fun whenLoadPrepend_thenReturnMediatorResultWithEndOfPaginationIsTrue() = runTest {
        // When
        val result = subject.load(
            LoadType.PREPEND,
            PagingState(listOf(), null, PagingConfig(20), 10),
        )

        // Then
        assertIs<MediatorResult.Success>(result)
        assertEquals(true, result.endOfPaginationReached)
    }

    @Test
    fun givenEmptyResponse_whenLoadAppend_thenLoadNextPageAndReturnMediatorResultWithEndOfPaginationIsTrue() = runTest {
        // Given
        val pageSlot = slot<Int>()
        every { preferenceDataSource.allCharacterNextPage } returns flowOf(2)
        coEvery { networkDataSource.getAllCharacter(capture(pageSlot)) } returns emptyList()

        // When
        val result = subject.load(
            LoadType.APPEND,
            PagingState(listOf(), null, PagingConfig(20), 10),
        )

        // Then
        assertEquals(2, pageSlot.captured)
        assertIs<MediatorResult.Success>(result)
        assertEquals(true, result.endOfPaginationReached)
    }

    @Test
    fun givenError_whenLoadRefresh_thenReturnMediatorResultError() = runTest {
        // Given
        val pageSlot = slot<Int>()
        coEvery { networkDataSource.getAllCharacter(capture(pageSlot)) } throws Error("error")

        // When
        val result = subject.load(
            LoadType.REFRESH,
            PagingState(listOf(), null, PagingConfig(20), 10),
        )

        // Then
        assertEquals(1, pageSlot.captured)
        assertIs<MediatorResult.Error>(result)
        assertEquals("error", result.throwable.message)
    }

    private fun buildCharacterResponse(id: Int) = CharacterResponse(
        id = id,
        name = "Name of $id",
        status = "status of $id",
        species = "species of $id",
        type = "sub species of $id",
        gender = "gender of $id",
        origin = OriginResponse(
            name = "origin of $id",
        ),
        location = LocationResponse(
            name = "location of $id",
        ),
        image = "image of $id",
        episode = listOf(
            "https://rickandmortyapi.com/api/episode/1",
            "https://rickandmortyapi.com/api/episode/2",
        ),
    )
}
