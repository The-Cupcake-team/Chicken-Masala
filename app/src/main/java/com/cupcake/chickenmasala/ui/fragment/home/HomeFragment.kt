package com.cupcake.chickenmasala.ui.fragment.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.cupcake.chickenmasala.R
import com.cupcake.chickenmasala.data.RepositoryImpl
import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.databinding.FragmentHomeBinding
import com.cupcake.chickenmasala.ui.base.BaseFragment
import com.cupcake.chickenmasala.ui.util.OnItemClickListener
import com.cupcake.chickenmasala.ui.fragment.health.HealthyFragment
import com.cupcake.chickenmasala.ui.fragment.details.DetailsFragment
import com.cupcake.chickenmasala.ui.fragment.home.adapter.HorizontalRecipeRecyclerAdapter
import com.cupcake.chickenmasala.ui.fragment.home.adapter.VerticalRecipeRecyclerAdapter
import com.cupcake.chickenmasala.ui.fragment.home.adapter.ViewPagerAdapter
import com.cupcake.chickenmasala.usecase.Repository
import com.cupcake.chickenmasala.usecase.home.GetHealthAdvicesUseCase
import com.cupcake.chickenmasala.usecase.home.GetRecentFoodUseCase
import com.cupcake.chickenmasala.utill.DataSourceProvider

class HomeFragment : BaseFragment<FragmentHomeBinding>(), OnItemClickListener<Recipe> {
    override val LOG_TAG: String = this::class.java.name

    override val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    private lateinit var horizontalRecipeRecyclerAdapter: HorizontalRecipeRecyclerAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var handler: Handler
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var verticalRecipeRecyclerAdapter: VerticalRecipeRecyclerAdapter

    private val repository: Repository by lazy {
        RepositoryImpl(DataSourceProvider.getDataSource(requireActivity().application))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager()
        setupHorizontalRecipeRecycler()
        setupVerticalRecipeRecyclerView()
        setUpTransformer()
        addCallbacks()
    }

    private fun setupHorizontalRecipeRecycler() {
        horizontalRecipeRecyclerAdapter = HorizontalRecipeRecyclerAdapter(this)
        val data = GetRecentFoodUseCase(repository)(RECENT_FOOD_LIMIT)
        horizontalRecipeRecyclerAdapter.submitList(data)
        binding.recyclerViewHorizontal.adapter = horizontalRecipeRecyclerAdapter
    }

    private fun setupVerticalRecipeRecyclerView() {
        verticalRecipeRecyclerAdapter = VerticalRecipeRecyclerAdapter(this)
        verticalRecipeRecyclerAdapter.submitList(repository.getRecipes())
        binding.recyclerViewVertical.adapter = verticalRecipeRecyclerAdapter
    }

    private fun addCallbacks() {
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 4000)
            }
        })

        binding.viewAll.setOnClickListener {
            // DishesFragment not implemented yet
            //  navigateToFragment(DishesFragment)
        }

        binding.viewPager.setOnClickListener {
            val healthyFragment = HealthyFragment.newInstance(id)
            navigateToFragment(healthyFragment)
        }
    }

    private fun setupViewPager() {
        handler = Handler(Looper.myLooper()!!)
        viewPager = binding.viewPager

        val advices = GetHealthAdvicesUseCase(repository)(ADVICES_LIMIT)
        viewPagerAdapter = ViewPagerAdapter(viewPager, advices)
        viewPagerAdapter.submitList(advices)

        viewPager.adapter = viewPagerAdapter
        viewPager.offscreenPageLimit = 3
        viewPager.clipToPadding = false
        viewPager.clipChildren = false
        viewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
    }

    private fun setUpTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - kotlin.math.abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }
        viewPager.setPageTransformer(transformer)
    }

    override fun onItemClicked(item: Recipe) {
        val detailsFragment = DetailsFragment.newInstance(item.id)
        navigateToFragment(detailsFragment)
    }

    private fun navigateToFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction().apply {
            add(R.id.fragmentContainer, fragment)
            addToBackStack(fragment.javaClass.simpleName)
            commit()
        }
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, 4000)
    }

    private val runnable = Runnable {
        viewPager.currentItem = viewPager.currentItem + 1
    }

    companion object {
        const val RECENT_FOOD_LIMIT = 10
        const val ADVICES_LIMIT = 7
    }

}


