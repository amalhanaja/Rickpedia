package dev.amalhanaja.rickpedia.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "character_episode",
    primaryKeys = ["character_id", "episode_id"],
)
data class CharacterEpisodeCrossRefEntity(
    @ColumnInfo("character_id")
    val characterId: Int,
    @ColumnInfo("episode_id")
    val episodeId: Int,
)
