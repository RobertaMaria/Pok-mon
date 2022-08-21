package com.example.pokedex.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import com.example.pokedex.domain.entity.PokemonDetail
import java.util.zip.Inflater

internal class PokemonListAdapter :
    PagingDataAdapter<PokemonDetail, PokemonListAdapter.PokemonListViewHolder>(DiffUtilCallBack()) {

    override fun onBindViewHolder(holder: PokemonListViewHolder, position: Int) {
        //getItem(position)?.let { holder.vincula(it) }
        holder.itemView.findViewById<TextView>(R.id.name_test).text = getItem(position)?.name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonListViewHolder {
        val inflate = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return PokemonListViewHolder(inflate)
    }

    class PokemonListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        fun vincula(position: PokemonDetail) {
//            itemView.findViewById<TextView>(R.id.name_test).text = position.name
//        }

    }

    class DiffUtilCallBack : DiffUtil.ItemCallback<PokemonDetail>() {
        override fun areItemsTheSame(oldItem: PokemonDetail, newItem: PokemonDetail): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: PokemonDetail, newItem: PokemonDetail): Boolean {
            return oldItem == newItem
        }

    }


}