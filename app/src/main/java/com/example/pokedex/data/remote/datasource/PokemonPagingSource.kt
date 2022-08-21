package com.example.pokedex.data.remote.datasource

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pokedex.data.remote.service.PokemonService
import com.example.pokedex.domain.entity.PokemonDetail

internal class PokemonPagingSource(private val service: PokemonService) : PagingSource<Int, PokemonDetail>() {

    override fun getRefreshKey(state: PagingState<Int, PokemonDetail>): Int? {
        return state.anchorPosition?.let { anchorPosition->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonDetail> {
        val nextPage = params.key ?: OFFSET_PAGE_INDEX
        val response = service.getPokeon(offset = nextPage)
        var nextOffset: Int? = null
        response.next?.let {
            val uri = Uri.parse(it)
            val proximoOffset = uri.getQueryParameter("offset")
             nextOffset = proximoOffset?.toInt()

        }

        val pokemonDetail = response.results.map {
            with(it){
                PokemonDetail(
                    name = name,
                    url = url
                )
            }
        }

        return LoadResult.Page(
            data = pokemonDetail,
            prevKey = null,
            nextKey = nextOffset
        )


    }

    companion object {
        private const val OFFSET_PAGE_INDEX = 0
    }
}