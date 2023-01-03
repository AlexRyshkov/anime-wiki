package com.example.animelist.presentation.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.animelist.*
import com.example.animelist.data.AnimeApiStatus
import com.example.animelist.data.HomeViewModel
import com.example.animelist.databinding.FragmentHomeBinding
import com.example.animelist.presentation.AnimeListAdapter
import com.example.animelist.di.database.Anime


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val homeViewModel: HomeViewModel by activityViewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility", "NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initScrollListener()

        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                homeViewModel.updateQuery(s.toString())
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        binding.searchEditText.setOnEditorActionListener { textView, actionId, _ ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                homeViewModel.searchWithQuery()
                textView.hideKeyboard()
                textView.clearFocus()
                handled = true
            }
            handled
        }

        binding.clearImageButton.setOnClickListener {
            homeViewModel.updateQuery("")
            homeViewModel.searchWithQuery()
            binding.searchEditText.setText("")
        }

        val animeClickListener: (Anime) -> Unit = { anime ->
            val bundle = Bundle()
            // Строковые литералы лучше выносить в коснтанты
            bundle.putInt("malId", anime.malId)
            Navigation.findNavController(view)
                .navigate(R.id.action_homeDest_to_animeInfoFragment, bundle)
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
                binding.animeRecyclerView.layoutManager = GridLayoutManager(
                    context,
                    getLayoutManagerSpanCount((requireActivity() as MainActivity).widthWindowSizeClass),
                    GridLayoutManager.VERTICAL,
                    false
                )
                binding.animeRecyclerView.adapter = AnimeListAdapter(animeList, animeClickListener)
            } else {
                binding.animeRecyclerView.adapter?.notifyDataSetChanged()
            }
        }

        homeViewModel.status.observe(viewLifecycleOwner, statusObserver)
        homeViewModel.animeList.observe(viewLifecycleOwner, animeListObserver)
        homeViewModel.query.observe(viewLifecycleOwner) {
            binding.clearImageButton.visibility = if (it.isEmpty()) View.GONE else View.VISIBLE
        }
    }

    private fun initScrollListener() {
        binding.animeRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val gridLayoutManager = recyclerView.layoutManager as GridLayoutManager
                if (homeViewModel.status.value != AnimeApiStatus.LOADING) {
                    if (gridLayoutManager.findLastCompletelyVisibleItemPosition() ==
                        homeViewModel.animeList.value?.size?.minus(1)
                    ) {
                        homeViewModel.nextPage()
                    }
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}