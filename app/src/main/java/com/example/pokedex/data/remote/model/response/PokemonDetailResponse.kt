package com.example.pokedex.data.remote.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PokemonDetailResponse(
    @SerialName("name") val name: String,
    @SerialName("url") val url: String
)
