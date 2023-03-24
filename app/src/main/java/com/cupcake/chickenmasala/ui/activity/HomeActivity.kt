package com.cupcake.chickenmasala.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.cupcake.chickenmasala.R
import com.cupcake.chickenmasala.usecase.DataManager
import com.cupcake.chickenmasala.data.DataManagerImpl
import com.cupcake.chickenmasala.data.model.HealthyAdvices
import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.databinding.ActivityMainBinding
import com.cupcake.chickenmasala.ui.fragment.CuisineFragment
import com.cupcake.chickenmasala.ui.fragment.HistoryFragment
import com.cupcake.chickenmasala.ui.fragment.HomeFragment
import com.cupcake.chickenmasala.ui.fragment.SearchFragment
import com.cupcake.chickenmasala.utill.TypeModel
import com.cupcake.chickenmasala.utill.parsers.CsvParser
import java.io.InputStreamReader


class HomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private lateinit var csvParser: CsvParser
     lateinit var dataManager: DataManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addNavigationListener()

        csvParser = CsvParser()
        initialDataManager()
    }

    private fun initialDataManager() {
        dataManager = DataManagerImpl(getRecipes(), getHealthyAdvice())
    }

    private fun getRecipes(): List<Recipe> {
        return csvParser.parseFile(openFile("Indian_food.csv"), TypeModel.RECIPE) as List<Recipe>
    }

    private fun getHealthyAdvice(): List<HealthyAdvices> {
        return csvParser.parseFile(
            openFile("health_advices.csv"),
            TypeModel.HEALTH_ADVICE
        ) as List<HealthyAdvices>
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

    private fun openFile(fileName: String): InputStreamReader {
        val inputStream = assets.open(fileName)
        return InputStreamReader(inputStream)
    }

}