package com.example.animelist.data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.animelist.R
import com.example.animelist.network.Anime

class AnimeListAdapter(
    private val context: Context?,
    private val items: List<Anime>
) : RecyclerView.Adapter<AnimeListAdapter.AnimeListViewholder>() {

    class AnimeListViewholder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewTitle = view.findViewById<TextView>(R.id.anime_title)
        val animeImageView = view.findViewById<ImageView>(R.id.anime_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeListViewholder {
        val layoutAdapter =
            LayoutInflater.from(parent.context).inflate(R.layout.fragment_anime_item, parent, false)
        return AnimeListViewholder(layoutAdapter)
    }

    override fun onBindViewHolder(holder: AnimeListViewholder, position: Int) {
        val item = items[position]
        holder.textViewTitle.text = item.title
        holder.animeImageView.load(item.images.webp.image_url)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}