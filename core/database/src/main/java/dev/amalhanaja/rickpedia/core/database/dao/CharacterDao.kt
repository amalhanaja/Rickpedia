package dev.amalhanaja.rickpedia.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import dev.amalhanaja.rickpedia.core.database.entity.CharacterEntity
import dev.amalhanaja.rickpedia.core.database.entity.CharacterEpisodeCrossRefEntity
import dev.amalhanaja.rickpedia.core.database.entity.CharacterWithEpisodeIdsEntity

@Dao
interface CharacterDao {

    @Upsert
    suspend fun insertCharacters(characters: List<CharacterEntity>)

    @Insert
    suspend fun insertCharacterEpisodeCrossRefs(characterEpisodeCrossRefs: List<CharacterEpisodeCrossRefEntity>)

    @Transaction
    @Query("SELECT * FROM characters")
    fun getAllCharacter(): PagingSource<Int, CharacterWithEpisodeIdsEntity>
}
