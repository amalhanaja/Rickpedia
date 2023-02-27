package dev.amalhanaja.rickpedia.core.data.mapper

import dev.amalhanaja.rickpedia.core.network.response.EpisodeResponse
import org.junit.Assert
import org.junit.Test
import kotlin.test.assertEquals

class EpisodeMapperTest {

    @Test
    fun givenNullId_whenResponseToEpisode_thenThrowIllegalArgumentException() {
        // Given
        val response = EpisodeResponse(id = null)

        // When
        val exception = Assert.assertThrows(IllegalArgumentException::class.java) {
            response.toEpisode()
        }

        // Then
        assertEquals("Required value was null.", exception.message)
    }

    @Test
    fun givenNullName_whenResponseToEpisode_thenThrowIllegalArgumentException() {
        // Given
        val response = EpisodeResponse(id = 1, name = null)

        // When
        val exception = Assert.assertThrows(IllegalArgumentException::class.java) {
            response.toEpisode()
        }

        // Then
        assertEquals("Required value was null.", exception.message)
    }

    @Test
    fun givenNullEpisode_whenResponseToEpisode_thenThrowIllegalArgumentException() {
        // Given
        val response = EpisodeResponse(id = 1, name = "Name", episode = null)

        // When
        val exception = Assert.assertThrows(IllegalArgumentException::class.java) {
            response.toEpisode()
        }

        // Then
        assertEquals("Required value was null.", exception.message)
    }

    @Test
    fun givenNullAirDate_whenResponseToEpisode_thenThrowIllegalArgumentException() {
        // Given
        val response = EpisodeResponse(id = 1, name = "Name", episode = "SE01E02", airDate = null)

        // When
        val exception = Assert.assertThrows(IllegalArgumentException::class.java) {
            response.toEpisode()
        }

        // Then
        assertEquals("Required value was null.", exception.message)
    }
}
