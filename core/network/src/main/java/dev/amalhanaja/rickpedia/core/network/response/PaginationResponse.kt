package dev.amalhanaja.rickpedia.core.network.response

import com.google.gson.annotations.SerializedName

internal data class PaginationResponse<T>(
    @SerializedName("results")
    val results: List<T>? = null,
)
