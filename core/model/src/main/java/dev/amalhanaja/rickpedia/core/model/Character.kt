package dev.amalhanaja.rickpedia.core.model

data class Character(
    val name: String,
    val status: CharacterStatus,
    val species: String,
    val subSpecies: String,
    val gender: String,
    val origin: Origin,
    val location: Location,
    val image: String,
    val episodeIds: List<Int>,
)
