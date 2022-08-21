package com.example.pokedex.di

import com.example.pokedex.data.local.database.PokemonDataBase
import com.example.pokedex.data.remote.datasource.PokemonPagingSource
import com.example.pokedex.data.remote.mediator.PokemonRemoteMediator
import com.example.pokedex.data.remote.service.PokemonService
import com.example.pokedex.data.repository.PokemonRepositoryImp
import com.example.pokedex.domain.repository.PokemonRepository
import com.example.pokedex.domain.usecase.GetPokemonUseCase
import com.example.pokedex.presentation.PokemonViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BESE_URL = "https://pokeapi.co/api/v2/"

val retrofitModule = module {
    factory<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BESE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    factory<OkHttpClient> {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

    }

    factory<PokemonService> {
        get<Retrofit>().create(PokemonService::class.java)
    }
}

val pokemonPagingSourceModule = module {
    factory<PokemonPagingSource> {
        PokemonPagingSource(service = get())
    }
}

val pokemonMediatorModule = module {
    factory<PokemonRemoteMediator> {
        PokemonRemoteMediator(service = get(), dataBase = PokemonDataBase.getInstance(androidContext()))
    }
}

val repositoryModule = module {
    factory<PokemonRepository> {
        PokemonRepositoryImp(mediator = get(), dataBase = PokemonDataBase.getInstance(androidContext()))
    }
}

val getPokemonUseCase = module {
    factory<GetPokemonUseCase> {
        GetPokemonUseCase(repository = get())
    }
}

val pokemonViewModel = module {
    factory<PokemonViewModel> {
        PokemonViewModel(getPokemonUseCase = get())
    }
}
val appModule = listOf(
    retrofitModule,
    pokemonPagingSourceModule,
    repositoryModule,
    getPokemonUseCase,
    pokemonViewModel,
    pokemonMediatorModule
)