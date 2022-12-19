package com.example.animelist

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.animelist.data.AnimeApiStatus
import com.example.animelist.data.AnimeListAdapter
import com.example.animelist.data.AnimeViewModel
import com.example.animelist.databinding.FragmentAnimeListBinding
import com.example.animelist.network.Anime


class AnimeListFragment : Fragment() {
    private lateinit var binding: FragmentAnimeListBinding
    private val animeViewModel: AnimeViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAnimeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initScrollListener()

        val animeClickListener = { anime:Anime ->
            val bundle = Bundle()
            bundle.putString("malId", anime.malId.toString())
            Navigation.findNavController(view).navigate(R.id.action_animeListFragment_to_animeInfoFragment2, bundle)
        }

        val statusObserver = Observer<AnimeApiStatus> { status ->
            if (status == AnimeApiStatus.LOADING) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }

        val animeListObserver = Observer<MutableList<Anime>> { animeList ->
            if (binding.animeRecyclerView.adapter == null) {
                binding.animeRecyclerView.adapter = AnimeListAdapter(animeList, animeClickListener)
            } else {
                binding.animeRecyclerView.adapter?.notifyDataSetChanged()
            }
        }

        animeViewModel.status.observe(viewLifecycleOwner, statusObserver)
        animeViewModel.animeList.observe(viewLifecycleOwner, animeListObserver)
    }

    private fun initScrollListener() {
        binding.animeRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val gridLayoutManager = recyclerView.layoutManager as GridLayoutManager
                if (animeViewModel.status.value != AnimeApiStatus.LOADING) {
                    if (gridLayoutManager.findLastCompletelyVisibleItemPosition() ==
                        animeViewModel.animeList.value?.size?.minus(1)
                    ) {
                        animeViewModel.nextPage()
                    }
                }
            }
        })
    }
}