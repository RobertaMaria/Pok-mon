package com.example.pokedex.data.remote.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.pokedex.data.local.database.PokemonDataBase
import com.example.pokedex.data.remote.service.PokemonService
import com.example.pokedex.domain.entity.PokemonDetail
import com.example.pokedex.domain.entity.RemoteKeys
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
internal class PokemonRemoteMediator(
    private val dataBase: PokemonDataBase,
    private val service: PokemonService
) : RemoteMediator<Int, PokemonDetail>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, PokemonDetail>): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 0
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                if (prevKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys =  getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                if (nextKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                nextKey
            }
        }

        try {
            val response = service.getPokeon(offset = page)
            val results = response.results
            val endOfPaginationReached = results.isEmpty()

            if (loadType == LoadType.REFRESH) {
                CoroutineScope(Dispatchers.IO).launch {
                    dataBase.remoteKeysDao().clearRemoteKeys()
                    dataBase.getPokemonDao().clearPokemon()
                }
            }
            val pokemonDetail: MutableList<PokemonDetail> = arrayListOf()

            val prevKey = if (page == 0) null else page - 10
            val nextKey = if (endOfPaginationReached) null else page + 10

            val keys = results.map {
                val regex = "/\\d+".toRegex()
                val id = regex.find(it.url)?.value?.replace("/", "")?.toInt()

                pokemonDetail.add(PokemonDetail(name = it.name, url = it.url))
                RemoteKeys(id = id!!.toInt(), prevKey = prevKey, nextKey = nextKey, name = it.name)
            }

            CoroutineScope(Dispatchers.IO).launch {
                dataBase.remoteKeysDao().insertAll(keys)
                dataBase.getPokemonDao().savePokemon(pokemonDetail)
            }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }

    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, PokemonDetail>): RemoteKeys? {
        val remoteKeys =  state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let {
            dataBase.remoteKeysDao().remoteKeysRepoId(it.name)
        }
        return remoteKeys
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, PokemonDetail>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { repo ->
                dataBase.remoteKeysDao().remoteKeysRepoId(repo.name)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, PokemonDetail>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.name?.let { repoId ->
                dataBase.remoteKeysDao().remoteKeysRepoId(repoId)
            }
        }
    }

}
