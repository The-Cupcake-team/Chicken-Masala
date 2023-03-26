package com.cupcake.chickenmasala.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.cupcake.chickenmasala.R
import com.cupcake.chickenmasala.databinding.ActivityMainBinding
import com.cupcake.chickenmasala.ui.fragment.cuisine.CuisineFragment
import com.cupcake.chickenmasala.ui.fragment.history.HistoryFragment
import com.cupcake.chickenmasala.ui.fragment.home.HomeFragment
import com.cupcake.chickenmasala.ui.fragment.search.SearchFragment


class HomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_ChickenMasala)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addNavigationListener()
    }


    private fun addNavigationListener() {

        val homeFragment = HomeFragment()
        val searchFragment = SearchFragment()
        val cuisineFragment = CuisineFragment()
        val historyFragment = HistoryFragment()

        binding.bottomNavigationBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home_fragment -> {
                    replaceFragment(homeFragment)
                    true
                }

                R.id.search_fragment -> {
                    replaceFragment(searchFragment)
                    true
                }
                R.id.cuisine_fragment -> {
                    replaceFragment(cuisineFragment)
                    true
                }
                R.id.history_fragment -> {
                    replaceFragment(historyFragment)
                    true
                }
                else -> false
            }

        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, fragment)
            commit()
        }
    }
}