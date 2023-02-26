package dev.amalhanaja.rickpedia.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.amalhanaja.rickpedia.core.database.dao.CharacterDao
import dev.amalhanaja.rickpedia.core.database.dao.EpisodeDao
import dev.amalhanaja.rickpedia.core.database.entity.CharacterEntity
import dev.amalhanaja.rickpedia.core.database.entity.CharacterEpisodeCrossRefEntity
import dev.amalhanaja.rickpedia.core.database.entity.EpisodeEntity

@Database(
    entities = [
        CharacterEntity::class,
        CharacterEpisodeCrossRefEntity::class,
        EpisodeEntity::class,
    ],
    version = 1,
    exportSchema = true,
)
abstract class RickpediaDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    abstract fun episodeDao(): EpisodeDao
}
