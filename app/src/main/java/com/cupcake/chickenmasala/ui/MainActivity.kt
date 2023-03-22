package com.cupcake.chickenmasala.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.cupcake.chickenmasala.R
import com.cupcake.chickenmasala.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val homeFragment = HomeFragment()
    private val searchFragment = SearchFragment()
    private val cuisineFragment = CuisineFragment()
    private val historyFragment = HistoryFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addNavigationListener()
    }

    private fun addNavigationListener() {
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