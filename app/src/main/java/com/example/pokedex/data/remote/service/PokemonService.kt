package com.example.pokedex.data.remote.service

import com.example.pokedex.data.remote.model.response.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface PokemonService {

    @GET("pokemon")
    suspend fun getPokeon(
        @Query("limit") limit: Int = 10,
        @Query("offset") offset: Int
    ): PokemonResponse
}