package com.example.pokedex.presentation

import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.databinding.ReposLoadStateFooterViewItemBinding

internal class ReposLoadStateViewHolder(
    private val binding: ReposLoadStateFooterViewItemBinding,
    private val retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.reposLoadStateFooterViewItemButton.setOnClickListener {
            retry.invoke()
        }
    }

}