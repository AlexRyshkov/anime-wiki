package com.example.animelist

import android.annotation.SuppressLint
import android.os.Bundle
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
import com.example.animelist.data.AnimeApiStatus
import com.example.animelist.data.AnimeListAdapter
import com.example.animelist.data.AnimeViewModel
import com.example.animelist.database.Anime
import com.example.animelist.databinding.FragmentAnimeListBinding


class AnimeListFragment : Fragment() {
    private var _binding: FragmentAnimeListBinding? = null
    private val animeViewModel: AnimeViewModel by activityViewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAnimeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Лучше разбить метод на более мелкие
    @SuppressLint("ClickableViewAccessibility", "NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initScrollListener()

        (activity as MainActivity).binding.bottomNav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> Navigation.findNavController(activity as MainActivity,R.id.navHostFragment)
                    .navigate(R.id.action_favoriteFragment_to_animeListFragment)
                R.id.favorite -> Navigation.findNavController(activity as MainActivity, R.id.navHostFragment)
                    .navigate(R.id.action_animeListFragment_to_favoriteFragment)
            }
            true
        }

        binding.searchEditText.setOnEditorActionListener { textView, actionId, _ ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                animeViewModel.updateQuery(textView.text.toString())
                textView.hideKeyboard()
                textView.clearFocus()
                handled = true
            }
            handled
        }

        val animeClickListener: (Anime) -> Unit = { anime ->
            val bundle = Bundle()
            // Строковые литералы лучше выносить в коснтанты
            bundle.putInt("malId", anime.malId)
            Navigation.findNavController(view)
                .navigate(R.id.action_animeListFragment_to_animeInfoFragment2, bundle)
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
                // Неправильно пересоздавать адаптеры
                binding.animeRecyclerView.adapter = AnimeListAdapter(animeList, animeClickListener)
            } else {
                // Перейти на diff utils
                binding.animeRecyclerView.adapter?.notifyDataSetChanged()
            }
        }

        animeViewModel.status.observe(viewLifecycleOwner, statusObserver)
        animeViewModel.animeList.observe(viewLifecycleOwner, animeListObserver)
    }

    private fun initScrollListener() {
        // Листенер добавляешь, но нигде потом не убираешь -> потенциальная утечка. Для отслеживания утечек памяти
        //  есть библиотека LeakCanary
        binding.animeRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val gridLayoutManager = recyclerView.layoutManager as GridLayoutManager
                // Вот эту штуку лучше во вью модель вынести, а фрагмент будет просто вызывать какой-то один метод
                if (animeViewModel.status.value != AnimeApiStatus.LOADING) {
                    if (gridLayoutManager.findLastCompletelyVisibleItemPosition() ==
                        // Такие простыни кода лучше выносить в отдельные функции / val get()
                        animeViewModel.animeList.value?.size?.minus(1)
                    ) {
                        animeViewModel.nextPage()
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