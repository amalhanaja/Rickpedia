package dev.amalhanaja.rickpedia.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import dev.amalhanaja.rickpedia.core.database.entity.EpisodeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface EpisodeDao {
    @Upsert
    suspend fun insertEpisodes(episodes: List<EpisodeEntity>)

    @Query("SELECT * FROM episodes WHERE id IN (:episodes)")
    fun getEpisodes(episodes: List<Int>): Flow<List<EpisodeEntity>>
}
