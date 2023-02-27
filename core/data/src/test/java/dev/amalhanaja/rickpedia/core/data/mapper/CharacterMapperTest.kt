package dev.amalhanaja.rickpedia.core.data.mapper

import dev.amalhanaja.rickpedia.core.database.entity.CharacterEntity
import dev.amalhanaja.rickpedia.core.database.entity.CharacterEpisodeCrossRefEntity
import dev.amalhanaja.rickpedia.core.database.entity.CharacterWithEpisodeIdsEntity
import dev.amalhanaja.rickpedia.core.model.Character
import dev.amalhanaja.rickpedia.core.network.response.CharacterResponse
import org.junit.Assert
import org.junit.Test
import kotlin.test.assertEquals


class CharacterMapperTest {

    @Test
    fun givenIdNull_whenCharacterResponseToCharacter_thenThrowIllegalArgumentException() {
        // Given
        val response = CharacterResponse(id = null)

        // When
        val exception = Assert.assertThrows(IllegalArgumentException::class.java) {
            response.toCharacter()
        }

        // Then
        assertEquals("Required value was null.", exception.message)
    }

    @Test
    fun givenNameNull_whenCharacterResponseToCharacter_thenThrowIllegalArgumentException() {
        // Given
        val response = CharacterResponse(id = 1, name = null)

        // When
        val exception = Assert.assertThrows(IllegalArgumentException::class.java) {
            response.toCharacter()
        }

        // Then
        assertEquals("Required value was null.", exception.message)
    }

    @Test
    fun givenNull_whenCharacterResponseToCharacter_thenReturnWithDefaultValue() {
        // Given
        val response = CharacterResponse(id = 1, name = "Alfian")

        // When
        val result = response.toCharacter()

        // Then
        assertEquals(1, result.id)
        assertEquals("Alfian", result.name)
        assertEquals("", result.status)
        assertEquals("", result.species)
        assertEquals("", result.subSpecies)
        assertEquals("", result.gender)
        assertEquals("", result.origin)
        assertEquals("", result.location)
        assertEquals("", result.image)
        assertEquals(emptyList(), result.episodeIds)
    }

    @Test
    fun whenCharacterEntityToCharacter_thenReturnExpectedCharacterEntity() {
        // Given
        val character = buildCharacterEntity()

        // When
        val result = character.toCharacter(listOf(1, 2, 3))

        // Then
        assertEquals(1, result.id)
        assertEquals("Name of 1", result.name)
        assertEquals("status of 1", result.status)
        assertEquals("species of 1", result.species)
        assertEquals("sub species of 1", result.subSpecies)
        assertEquals("gender of 1", result.gender)
        assertEquals("origin of 1", result.origin)
        assertEquals("location of 1", result.location)
        assertEquals("image of 1", result.image)
        assertEquals(listOf(1, 2, 3), result.episodeIds)
    }

    @Test
    fun whenCharacterWithEpisodeIdsToCharacter_thenReturnExpectedCharacter() {
        // Given
        val entity = CharacterWithEpisodeIdsEntity(
            buildCharacterEntity(),
            listOf(CharacterEpisodeCrossRefEntity(1, 1), CharacterEpisodeCrossRefEntity(1, 2)),
        )

        // When
        val result = entity.toCharacter()

        // Then
        assertEquals(
            Character(
                id = 1,
                name = "Name of 1",
                status = "status of 1",
                species = "species of 1",
                subSpecies = "sub species of 1",
                gender = "gender of 1",
                origin = "origin of 1",
                location = "location of 1",
                image = "image of 1",
                listOf(1, 2),
            ),
            result,
        )
    }

    private fun buildCharacterEntity() = CharacterEntity(
        id = 1,
        name = "Name of 1",
        status = "status of 1",
        species = "species of 1",
        subSpecies = "sub species of 1",
        gender = "gender of 1",
        origin = "origin of 1",
        location = "location of 1",
        image = "image of 1",
    )
}
