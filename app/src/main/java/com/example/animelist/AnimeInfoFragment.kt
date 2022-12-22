package com.example.animelist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import coil.clear
import coil.load
import com.example.animelist.data.AnimeApiStatus
import com.example.animelist.data.AnimeViewModel
import com.example.animelist.databinding.FragmentAnimeInfoBinding
import com.example.animelist.data.model.Anime


class AnimeInfoFragment : Fragment() {
    private lateinit var binding: FragmentAnimeInfoBinding
    private val animeViewModel: AnimeViewModel by activityViewModels()

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

        val animeObserver = Observer<Anime?> { anime ->
            if (anime != null) {
                binding.appBarTitleTextView.text = anime.title
                binding.posterImageView.load(anime.images.webp.large_image_url)
                binding.titleTextView.text = getString(R.string.title, anime.title)
                binding.episodesTextView.text = getString(R.string.episodes, anime.episodes)
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