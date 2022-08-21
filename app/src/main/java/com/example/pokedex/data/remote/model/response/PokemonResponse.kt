package com.example.pokedex.data.remote.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PokemonResponse(
    @SerialName("next") val next: String?,
    @SerialName("results") val results: List<PokemonDetailResponse>
)