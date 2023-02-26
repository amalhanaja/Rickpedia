package dev.amalhanaja.rickpedia.core.network.api

import com.google.gson.Gson
import dev.amalhanaja.rickpedia.core.network.response.CharacterResponse
import dev.amalhanaja.rickpedia.core.network.response.EpisodeResponse
import dev.amalhanaja.rickpedia.core.network.response.LocationResponse
import dev.amalhanaja.rickpedia.core.network.response.OriginResponse
import io.mockk.every
import io.mockk.mockkObject
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class RickpediaApiClientTest {

    private lateinit var subject: RickpediaApiClient

    @Before
    fun setUp() {
        subject = RickpediaApiClient(Gson(), OkHttpClient.Builder().build())
    }

    @Test
    fun whenGetAllCharacterWithResultNull_thenReturnEmptyList() = runTestWithMockWebServer(200, "all-character-null.json") {
        // When
        val result = subject.getAllCharacter(3)

        // Then
        val request = takeRequest()
        assertEquals("/character?page=3", request.path)
        assertEquals(0, result.count())
    }

    @Test
    fun whenGetAllCharacter_thenReturnListOfCharacter() = runTestWithMockWebServer(200, "all-character.json") {
        // When
        val result = subject.getAllCharacter(3)

        // Then
        val request = takeRequest()
        assertEquals("/character?page=3", request.path)
        assertEquals(1, result.count())
        assertEquals(
            CharacterResponse(
                id = 20,
                name = "Ants in my Eyes Johnson",
                status = "unknown",
                species = "Human",
                type = "Human with ants in his eyes",
                gender = "Male",
                origin = OriginResponse("unknown", ""),
                location = LocationResponse("Interdimensional Cable", "https://rickandmortyapi.com/api/location/6"),
                image = "https://rickandmortyapi.com/api/character/avatar/20.jpeg",
                episode = listOf("https://rickandmortyapi.com/api/episode/8"),
                url = "https://rickandmortyapi.com/api/character/20",
                created = "2017-11-04T22:34:53.659Z",
            ),
            result.first(),
        )
    }

    @Test
    fun whenGetEpisodes_thenReturnEpisodeResponse() = runTestWithMockWebServer(200, "episodes-1-2.json") {
        // When
        val result = subject.getEpisodes(listOf(1, 2))

        // Then
        val request = takeRequest()
        assertEquals("/episode/1,2", request.path)
        assertEquals(
            listOf(
                EpisodeResponse(
                    id = 1,
                    name = "Pilot",
                    airDate = "December 2, 2013",
                    episode = "S01E01",
                    characters = listOf("https://rickandmortyapi.com/api/character/2"),
                    url = "https://rickandmortyapi.com/api/episode/1",
                    created = "2017-11-10T12:56:33.798Z",
                ),
                EpisodeResponse(
                    id = 2,
                    name = "Lawnmower Dog",
                    airDate = "December 9, 2013",
                    episode = "S01E02",
                    characters = listOf("https://rickandmortyapi.com/api/character/1"),
                    url = "https://rickandmortyapi.com/api/episode/2",
                    created = "2017-11-10T12:56:33.916Z",
                ),
            ),
            result,
        )
    }

    private fun runTestWithMockWebServer(
        responseCode: Int,
        responseFileName: String,
        fn: suspend MockWebServer.() -> Unit,
    ) = runTest {
        mockkObject(RickpediaBaseUrlProvider) {
            val mockWebServer = MockWebServer()
            val response = MockResponse()
                .setResponseCode(responseCode)
                .setBody(readResource(responseFileName))
            mockWebServer.enqueue(response)
            every { RickpediaBaseUrlProvider.provideBaseUrl() } returns mockWebServer.url("/").toString()
            fn(mockWebServer)
            mockWebServer.shutdown()
        }
    }

    private fun readResource(fileName: String): String {
        val inputStream = requireNotNull(javaClass.classLoader?.getResourceAsStream(fileName))
        val source = inputStream.source().buffer()
        return source.readString(Charsets.UTF_8)
    }
}
