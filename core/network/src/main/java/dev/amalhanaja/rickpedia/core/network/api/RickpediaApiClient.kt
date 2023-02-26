package dev.amalhanaja.rickpedia.core.network.api

import com.google.gson.Gson
import dev.amalhanaja.rickpedia.core.network.RickpediaNetworkDataSource
import dev.amalhanaja.rickpedia.core.network.response.CharacterResponse
import dev.amalhanaja.rickpedia.core.network.response.EpisodeResponse
import dev.amalhanaja.rickpedia.core.network.response.PaginationResponse
import okhttp3.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Inject
import javax.inject.Singleton

private interface RickpediaService {
    @GET("character")
    suspend fun getAllCharacter(
        @Query("page") page: Int,
    ): PaginationResponse<CharacterResponse>

    @GET("episode/{episodes}")
    suspend fun getEpisodes(
        @Path("episodes") episodes: String,
    ): List<EpisodeResponse>
}

@Singleton
class RickpediaApiClient @Inject constructor(
    gson: Gson,
    callFactory: Call.Factory,
) : RickpediaNetworkDataSource {

    private val service: RickpediaService by lazy {
        Retrofit.Builder()
            .baseUrl(RickpediaBaseUrlProvider.provideBaseUrl())
            .callFactory(callFactory)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create()
    }

    override suspend fun getAllCharacter(page: Int): List<CharacterResponse> {
        return service.getAllCharacter(page).results.orEmpty()
    }

    override suspend fun getEpisodes(episodes: List<Int>): List<EpisodeResponse> {
        return service.getEpisodes(episodes.joinToString(","))
    }
}
