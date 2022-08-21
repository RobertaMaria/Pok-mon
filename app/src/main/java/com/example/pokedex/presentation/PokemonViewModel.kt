package com.example.pokedex.presentation

import androidx.lifecycle.ViewModel
import com.example.pokedex.domain.usecase.GetPokemonUseCase
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pokedex.domain.entity.PokemonDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

internal class PokemonViewModel(private val getPokemonUseCase: GetPokemonUseCase) : ViewModel() {

    fun getPokemon(): Flow<PagingData<PokemonDetail>> {
        return getPokemonUseCase().cachedIn(viewModelScope)
    }
}
