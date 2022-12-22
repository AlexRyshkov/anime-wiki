package com.example.animelist

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.animelist.data.AnimeViewModel
import com.example.animelist.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val animeViewModel: AnimeViewModel by viewModels()
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

//        binding.bottomNav.setOnNavigationItemSelectedListener {
//            when (it.itemId) {
//                R.id.home -> setCurrentFragment(AnimeListFragment())
//                R.id.favorite -> setCurrentFragment(FavoriteFragment())
//            }
//            true
//        }
    }

    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.navHostFragment, fragment)
            commit()
        }
}