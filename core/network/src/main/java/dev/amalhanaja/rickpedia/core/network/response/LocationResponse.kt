package dev.amalhanaja.rickpedia.core.network.response

import com.google.gson.annotations.SerializedName

data class LocationResponse(
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("url")
    val url: String? = null,
)
