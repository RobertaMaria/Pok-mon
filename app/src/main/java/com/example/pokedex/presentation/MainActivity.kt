package com.example.pokedex.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import com.example.pokedex.domain.usecase.GetPokemonUseCase
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewMode: PokemonViewModel by viewModel()

        val recyclerView = findViewById<RecyclerView>(R.id.reciclerview_test)
        val adapter = PokemonListAdapter()
        recyclerView.adapter = adapter
        lifecycleScope.launchWhenCreated {
            viewMode.getPokemon().collectLatest {
                adapter.submitData(it)
            }
        }
    }
}