package com.example.pokedex.domain.usecase

import androidx.paging.PagingData
import com.example.pokedex.domain.entity.PokemonDetail
import com.example.pokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow

internal class GetPokemonUseCase(private val repository: PokemonRepository) {

    operator fun invoke(): Flow<PagingData<PokemonDetail>> {
        return repository.getPokemon()
    }
}