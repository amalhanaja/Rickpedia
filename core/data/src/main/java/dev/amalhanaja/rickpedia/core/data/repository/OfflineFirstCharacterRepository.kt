package dev.amalhanaja.rickpedia.core.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import dev.amalhanaja.rickpedia.core.data.mapper.toCharacter
import dev.amalhanaja.rickpedia.core.data.paging.CharacterRemoteMediator
import dev.amalhanaja.rickpedia.core.database.dao.CharacterDao
import dev.amalhanaja.rickpedia.core.datastore.RickpediaPreferenceDataSource
import dev.amalhanaja.rickpedia.core.model.Character
import dev.amalhanaja.rickpedia.core.network.RickpediaNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val CHARACTER_PAGE_SIZE = 20

class OfflineFirstCharacterRepository @Inject constructor(
    private val networkDataSource: RickpediaNetworkDataSource,
    private val preferenceDataSource: RickpediaPreferenceDataSource,
    private val characterDao: CharacterDao,
) : CharacterRepository {
    override fun getAllCharacter(): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(pageSize = CHARACTER_PAGE_SIZE),
            remoteMediator = CharacterRemoteMediator(
                characterDao,
                networkDataSource,
                preferenceDataSource,
            ),
            pagingSourceFactory = { characterDao.getAllCharacter() },
        ).flow.map { data ->
            data.map { it.toCharacter() }
        }
    }
}
