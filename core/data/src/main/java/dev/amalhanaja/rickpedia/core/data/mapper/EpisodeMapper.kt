package dev.amalhanaja.rickpedia.core.data.mapper

import dev.amalhanaja.rickpedia.core.database.entity.EpisodeEntity
import dev.amalhanaja.rickpedia.core.model.Episode
import dev.amalhanaja.rickpedia.core.network.response.EpisodeResponse

fun EpisodeResponse.toEpisode(): Episode = Episode(
    id = requireNotNull(id),
    name = requireNotNull(name),
    code = requireNotNull(episode),
    airDate = requireNotNull(airDate),
)

fun EpisodeEntity.toEpisode(): Episode = Episode(
    id = id,
    name = name,
    code = code,
    airDate = airDate,
)

fun Episode.toEpisodeEntity(): EpisodeEntity = EpisodeEntity(
    id = id,
    name = name,
    code = code,
    airDate = airDate,
)
