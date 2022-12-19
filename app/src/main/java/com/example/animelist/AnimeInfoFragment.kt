package com.example.animelist

import AnimeInfoViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import coil.load
import com.example.animelist.databinding.FragmentAnimeInfoBinding
import com.example.animelist.network.Anime


class AnimeInfoFragment : Fragment() {
    private lateinit var binding: FragmentAnimeInfoBinding
    private val animeInfoViewModel: AnimeInfoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAnimeInfoBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val animeObserver = Observer<Anime> { anime ->
            binding.titleImageView.load(anime.images.webp.image_url)
            binding.titleTextView.text = anime.title
        }

        animeInfoViewModel.anime.observe(viewLifecycleOwner, animeObserver)

        val malId = arguments?.getString("malId")?.toInt()
        if (malId != null) {
            animeInfoViewModel.getAnime(malId)
        }
    }
}