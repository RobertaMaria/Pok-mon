package com.example.pokedex.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon")
internal data class PokemonDetail(
    val name: String,
    val url: String
){
    @PrimaryKey(autoGenerate = true)
    var id = 0
}
