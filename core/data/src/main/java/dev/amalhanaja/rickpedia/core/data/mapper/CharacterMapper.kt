package dev.amalhanaja.rickpedia.core.data.mapper

import androidx.paging.PagingData
import androidx.paging.map
import dev.amalhanaja.rickpedia.core.database.entity.CharacterEntity
import dev.amalhanaja.rickpedia.core.database.entity.CharacterWithEpisodeIdsEntity
import dev.amalhanaja.rickpedia.core.model.Character
import dev.amalhanaja.rickpedia.core.network.response.CharacterResponse

fun CharacterResponse.toCharacter(): Character = Character(
    id = requireNotNull(id),
    name = requireNotNull(name),
    status = status.orEmpty(),
    species = species.orEmpty(),
    subSpecies = type.orEmpty(),
    gender = gender.orEmpty(),
    origin = origin?.name.orEmpty(),
    location = location?.name.orEmpty(),
    image = image.orEmpty(),
    episodeIds = episode.orEmpty().map {
        it.substringAfterLast("/").toInt()
    },
)

fun Character.toCharacterEntity(): CharacterEntity = CharacterEntity(
    id = id,
    name = name,
    status = status,
    species = species,
    subSpecies = subSpecies,
    gender = gender,
    origin = origin,
    location = location,
    image = image,
)

fun CharacterEntity.toCharacter(episodeIds: List<Int>): Character = Character(
    id = id,
    name = name,
    status = status,
    species = species,
    subSpecies = subSpecies,
    gender = gender,
    origin = origin,
    location = location,
    image = image,
    episodeIds = episodeIds,
)

fun CharacterWithEpisodeIdsEntity.toCharacter(): Character = character.toCharacter(characterEpisodeCrossRefEntity.map { it.episodeId })

fun PagingData<CharacterWithEpisodeIdsEntity>.toPagingDataCharacter(): PagingData<Character> {
    return map { it.toCharacter() }
}
