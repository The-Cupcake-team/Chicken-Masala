package com.cupcake.chickenmasala.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.cupcake.chickenmasala.R
import com.cupcake.chickenmasala.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
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
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.search_fragment -> {
                    replaceFragment(SearchFragment())
                    true
                }
                R.id.cuisine_fragment -> {
                    replaceFragment(CuisineFragment())
                    true
                }
                R.id.history_fragment -> {
                    replaceFragment(HistoryFragment())
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