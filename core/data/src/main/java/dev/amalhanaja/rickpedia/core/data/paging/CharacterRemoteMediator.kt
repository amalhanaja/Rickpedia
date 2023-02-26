package dev.amalhanaja.rickpedia.core.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import dev.amalhanaja.rickpedia.core.data.mapper.toCharacter
import dev.amalhanaja.rickpedia.core.data.mapper.toCharacterEntity
import dev.amalhanaja.rickpedia.core.database.dao.CharacterDao
import dev.amalhanaja.rickpedia.core.database.entity.CharacterEpisodeCrossRefEntity
import dev.amalhanaja.rickpedia.core.database.entity.CharacterWithEpisodeIdsEntity
import dev.amalhanaja.rickpedia.core.datastore.RickpediaPreferenceDataSource
import dev.amalhanaja.rickpedia.core.network.RickpediaNetworkDataSource
import kotlinx.coroutines.flow.firstOrNull

private const val INITIAL_PAGE = 1
private const val PAGE_INCREMENT = 1

class CharacterRemoteMediator(
    private val characterDao: CharacterDao,
    private val networkDataSource: RickpediaNetworkDataSource,
    private val preferenceDataSource: RickpediaPreferenceDataSource,
) : RemoteMediator<Int, CharacterWithEpisodeIdsEntity>() {
    @ExperimentalPagingApi
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterWithEpisodeIdsEntity>,
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> preferenceDataSource.allCharacterNextPage.firstOrNull()
            } ?: INITIAL_PAGE
            val characters = networkDataSource.getAllCharacter(page).map { it.toCharacter() }
            if (characters.isEmpty()) return MediatorResult.Success(endOfPaginationReached = true)
            preferenceDataSource.setAllCharacterNextPage(page + PAGE_INCREMENT)
            characterDao.insertCharacters(characters.map { it.toCharacterEntity() })
            characterDao.insertCharacterEpisodeCrossRefs(
                characters.map { character ->
                    character.episodeIds.map { episodeId ->
                        CharacterEpisodeCrossRefEntity(character.id, episodeId)
                    }
                }.flatten(),
            )
            MediatorResult.Success(false)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
