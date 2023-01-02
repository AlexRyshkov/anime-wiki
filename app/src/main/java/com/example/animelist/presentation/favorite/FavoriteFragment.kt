package com.example.animelist.presentation.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.animelist.R
import com.example.animelist.databinding.FragmentFavoriteBinding
import com.example.animelist.di.AnimeListAdapter
import com.example.animelist.di.database.Anime
import com.example.animelist.presentation.info.FavoriteViewModel

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val homeViewModel: FavoriteViewModel by activityViewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val favoriteListObserver = Observer<List<Anime>> {
            if (binding.favoriteRecyclerView.adapter == null) {
                binding.favoriteRecyclerView.adapter = AnimeListAdapter(
                    it
                ) { anime ->
                    val bundle = Bundle()
                    bundle.putInt("malId", anime.malId)
                    findNavController().navigate(R.id.animeInfoDest, bundle)
                }
            } else {
                binding.favoriteRecyclerView.adapter?.notifyDataSetChanged()
            }
        }

        homeViewModel.favoriteList.observe(viewLifecycleOwner, favoriteListObserver)
    }
}