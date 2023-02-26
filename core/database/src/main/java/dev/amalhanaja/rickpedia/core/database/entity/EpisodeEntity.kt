package dev.amalhanaja.rickpedia.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "episodes")
data class EpisodeEntity(
    @ColumnInfo("id")
    @PrimaryKey
    val id: Int,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("code")
    val code: String,
    @ColumnInfo("air_date")
    val airDate: String,
)
