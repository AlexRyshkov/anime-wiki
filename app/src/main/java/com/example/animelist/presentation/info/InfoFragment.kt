package com.example.animelist.presentation.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.animelist.R
import com.example.animelist.data.AnimeApiStatus
import com.example.animelist.data.AnimeViewModel
import com.example.animelist.data.database.Anime
import com.example.animelist.databinding.FragmentAnimeInfoBinding


class InfoFragment : Fragment() {
    private lateinit var binding: FragmentAnimeInfoBinding
    private val infoViewModel: InfoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAnimeInfoBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.favoriteButton.setOnClickListener {
            val anime = animeViewModel.anime.value!!
            if (animeViewModel.isInFavorite(anime.malId)) {
                animeViewModel.removeFromFavorite(anime)
                binding.favoriteButton.setImageResource(R.drawable.ic_heart_plus)
                Toast.makeText(context, "Removed from favorite", Toast.LENGTH_SHORT).show()
            }
            else {
                animeViewModel.addToFavorite(anime)
                binding.favoriteButton.setImageResource(R.drawable.ic_heart_minus)
                Toast.makeText(context, "Added to favorite", Toast.LENGTH_SHORT).show()
            }
        }

        val animeObserver = Observer<Anime?> { anime ->
            if (anime != null) {
                binding.appBarTitleTextView.text = anime.title
                binding.posterImageView.load(anime.image)
                binding.titleTextView.text = getString(R.string.title, anime.title)
                binding.episodesTextView.text = getString(R.string.episodes, anime.episodes)
                binding.favoriteButton.setImageResource(if (animeViewModel.isInFavorite(anime.malId)) R.drawable.ic_heart_minus else R.drawable.ic_heart_plus)
            }
        }

        val statusObserver = Observer<AnimeApiStatus> { status ->
            if (status == AnimeApiStatus.LOADING) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

        animeViewModel.anime.observe(viewLifecycleOwner, animeObserver)
        animeViewModel.status.observe(viewLifecycleOwner, statusObserver)

        val malId = arguments?.getInt("malId")
        if (malId != null) {
            animeViewModel.getAnime(malId)
        }
    }

    override fun onDestroyView() {
        animeViewModel.clearAnime()
        super.onDestroyView()
    }
}