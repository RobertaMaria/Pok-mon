package com.example.pokedex.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.pokedex.domain.entity.PokemonDetail

@Dao
internal interface PokemonDao {

    @Insert(onConflict = REPLACE)
    fun savePokemon(pokemon: List<PokemonDetail>)

    @Query("DELETE FROM pokemon")
    fun clearPokemon()

    @Query("SELECT* FROM pokemon")
    fun reposByName(): PagingSource<Int, PokemonDetail>
}