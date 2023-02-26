package dev.amalhanaja.rickpedia.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey
    @ColumnInfo("id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo("status")
    val status: String,
    @ColumnInfo("species")
    val species: String,
    @ColumnInfo("sub_species")
    val subSpecies: String,
    @ColumnInfo("gender")
    val gender: String,
    @ColumnInfo("origin")
    val origin: String,
    @ColumnInfo("location")
    val location: String,
    @ColumnInfo("image")
    val image: String,
)
