package com.example.animelist.presentation.info

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.animelist.R
import com.example.animelist.databinding.FragmentAnimeInfoBinding
import com.example.animelist.di.database.Anime
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AnimeInfoFragment : Fragment() {
    private lateinit var binding: FragmentAnimeInfoBinding
    private val animeInfoViewModel: AnimeInfoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnimeInfoBinding.inflate(inflater, container, false)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        with (binding) {
            backButton.setOnClickListener {
                findNavController().popBackStack()
            }

            shareButton.setOnClickListener {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, animeInfoViewModel.anime.value?.url)
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }

            favoriteButton.setOnClickListener {
                val anime = animeInfoViewModel.anime.value!!
                if (animeInfoViewModel.isInFavorite(anime.malId)) {
                    animeInfoViewModel.removeFavorite(anime)
                    favoriteButton.setImageResource(R.drawable.ic_heart_plus)
                    Toast.makeText(context, "Removed from favorite", Toast.LENGTH_SHORT).show()
                }
                else {
                    animeInfoViewModel.addToFavorite(anime)
                    favoriteButton.setImageResource(R.drawable.ic_heart_minus)
                    Toast.makeText(requireActivity().baseContext, "Added to favorite", Toast.LENGTH_SHORT).show()
                }
            }

            val animeObserver = Observer<Anime?> { anime ->
                if (anime != null) {
                    appBarTitleTextView.text = anime.title
                    posterImageView.load(anime.image)
                    titleTextView.text = getString(R.string.title, anime.title)
                    episodesTextView.text = getString(R.string.episodes, anime.episodes)
                    favoriteButton.setImageResource(if (animeInfoViewModel.isInFavorite(anime.malId)) R.drawable.ic_heart_minus else R.drawable.ic_heart_plus)
                    scoreTextView.text = "Score: ${anime.score.toString()}"
                    val score = anime.score!!
                    for (i in 1..10) {
                        val imageView = ImageView(context)
                        imageView.layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        if (i < score) {
                            imageView.setImageResource(R.drawable.ic_star_fill)
                        }
                        else {
                            imageView.setImageResource(if (i - score < 1) R.drawable.ic_start_half else R.drawable.ic_star)
                        }
                        starsLinearLayout.addView(imageView)
                    }
                }
            }

            val statusObserver = Observer<ApiFetchStatus> { status ->
                if (status == ApiFetchStatus.LOADING) {
                    progressBar.visibility = View.VISIBLE
                } else {
                    progressBar.visibility = View.GONE
                }
            }

            animeInfoViewModel.anime.observe(viewLifecycleOwner, animeObserver)
            animeInfoViewModel.status.observe(viewLifecycleOwner, statusObserver)
        }
    }
}