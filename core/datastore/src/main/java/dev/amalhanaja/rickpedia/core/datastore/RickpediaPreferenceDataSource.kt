package dev.amalhanaja.rickpedia.core.datastore

import kotlinx.coroutines.flow.Flow

interface RickpediaPreferenceDataSource {

    val allCharacterNextPage: Flow<Int?>

    suspend fun setAllCharacterNextPage(nextPage: Int)
}
