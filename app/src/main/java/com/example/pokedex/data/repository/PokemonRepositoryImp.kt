package com.example.pokedex.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.pokedex.data.local.database.PokemonDataBase
import com.example.pokedex.data.remote.datasource.PokemonPagingSource
import com.example.pokedex.data.remote.mediator.PokemonRemoteMediator
import com.example.pokedex.domain.entity.PokemonDetail
import com.example.pokedex.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow

internal class PokemonRepositoryImp(private val mediator: PokemonRemoteMediator, private val dataBase: PokemonDataBase) : PokemonRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getPokemon(): Flow<PagingData<PokemonDetail>> {
        return Pager(config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false,
            prefetchDistance = 50,
            maxSize = 10 + 50 * 3
        ),
            remoteMediator = mediator,
            pagingSourceFactory = {dataBase.getPokemonDao().reposByName()}).flow
    }
}