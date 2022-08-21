package com.example.pokedex.domain.repository

import androidx.paging.PagingData
import com.example.pokedex.domain.entity.PokemonDetail
import kotlinx.coroutines.flow.Flow

internal interface PokemonRepository {
    fun getPokemon(): Flow<PagingData<PokemonDetail>>
}