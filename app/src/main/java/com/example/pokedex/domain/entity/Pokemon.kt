package com.example.pokedex.domain.entity

internal data class Pokemon(
    val next: String?,
    val results: List<PokemonDetail>?
)