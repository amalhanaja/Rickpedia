package dev.amalhanaja.rickpedia.core.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class CharacterWithEpisodeIdsEntity(

    @Embedded
    val character: CharacterEntity,

    @Relation(
        entity = CharacterEpisodeCrossRefEntity::class,
        parentColumn = "id",
        entityColumn = "character_id",
    )
    val characterEpisodeCrossRefEntity: List<CharacterEpisodeCrossRefEntity>,
)
