package dev.amalhanaja.rickpedia.core.data.repository

import androidx.paging.PagingData
import dev.amalhanaja.rickpedia.core.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    fun getAllCharacter(): Flow<PagingData<Character>>
}
