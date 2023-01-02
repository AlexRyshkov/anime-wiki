package com.example.animelist.di

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.animelist.databinding.FragmentAnimeItemBinding
import com.example.animelist.di.database.Anime

class AnimeListAdapter(
    private val items: List<Anime>,
    // Можно просто Anime, давать имя в типа странно и не нужно
    private val onItemClick: (anime: Anime) -> Unit
) : RecyclerView.Adapter<AnimeListAdapter.AnimeListViewholder>() {


    class AnimeListViewholder(binding: FragmentAnimeItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val animeImageView = binding.animeImage
        val textViewTitle = binding.animeTitle
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeListViewholder {
        val binding = FragmentAnimeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimeListViewholder(binding)
    }

    // Логику внутри этого метода лучше инкапсулировать в ViewHolder
    override fun onBindViewHolder(holder: AnimeListViewholder, position: Int) {
        val item = items[position]
        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
        holder.textViewTitle.text = item.title
        holder.animeImageView.load(item.image)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}