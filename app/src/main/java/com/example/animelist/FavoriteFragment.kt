package com.example.animelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.animelist.data.AnimeListAdapter
import com.example.animelist.data.AnimeViewModel
import com.example.animelist.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val animeViewModel: AnimeViewModel by activityViewModels()

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
        binding.favoriteRecyclerView.adapter = AnimeListAdapter(animeViewModel.favoriteList.value!!
        ) { anime ->
            val bundle = Bundle()
            bundle.putInt("malId", anime.malId)
            findNavController().navigate(R.id.animeInfoDest, bundle)
        }
    }
}