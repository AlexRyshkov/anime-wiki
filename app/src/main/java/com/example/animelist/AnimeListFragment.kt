package com.example.animelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.animelist.data.AnimeListAdapter
import com.example.animelist.data.AnimeViewModel
import com.example.animelist.databinding.FragmentAnimeListBinding
import com.example.animelist.network.Anime


class AnimeListFragment : Fragment() {
    private lateinit var binding :FragmentAnimeListBinding
    private val animeViewModel : AnimeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAnimeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val animeListObserver = Observer<List<Anime>> { animeList ->
            binding.animeRecyclerView.adapter = AnimeListAdapter(context, animeList)
        }
        animeViewModel.animeList.observe(viewLifecycleOwner, animeListObserver)
        binding.animeRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    animeViewModel.nextPage()
                }
            }
        })
    }
}