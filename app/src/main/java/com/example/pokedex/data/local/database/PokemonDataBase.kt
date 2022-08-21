package com.example.pokedex.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pokedex.data.local.dao.PokemonDao
import com.example.pokedex.data.local.dao.RemoteKeysDao
import com.example.pokedex.domain.entity.PokemonDetail
import com.example.pokedex.domain.entity.RemoteKeys

@Database(entities = [PokemonDetail::class, RemoteKeys::class], version = 1, exportSchema = false)
internal abstract class PokemonDataBase : RoomDatabase() {

    abstract fun getPokemonDao(): PokemonDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object{
        fun getInstance(context: Context): PokemonDataBase {
            return Room.databaseBuilder(context, PokemonDataBase::class.java, "pokemon").build()
        }
    }

}