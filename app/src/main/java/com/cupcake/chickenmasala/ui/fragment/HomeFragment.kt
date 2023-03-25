package com.cupcake.chickenmasala.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.cupcake.chickenmasala.ui.base.BaseFragment
import com.cupcake.chickenmasala.R
import com.cupcake.chickenmasala.databinding.FragmentHomeBinding
import com.cupcake.chickenmasala.ui.activity.HomeActivity
import com.cupcake.chickenmasala.ui.adapter.home.VerticalRecipeAdapter
import com.cupcake.chickenmasala.ui.adapter.home.HorizontalRecipeAdapter
import com.cupcake.chickenmasala.utill.ImageAdapter
import java.lang.Math.abs

class HomeFragment: BaseFragment<FragmentHomeBinding>() {
    override val LOG_TAG: String
        get() = "HOME_FRAGMENT"
    override val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    private lateinit var healthyViewPager: ViewPager2
    private lateinit var handler: Handler
    private lateinit var imageList: ArrayList<Int>
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var horizontalRecipeAdapter: HorizontalRecipeAdapter
    private lateinit var verticalRecipeAdapter: VerticalRecipeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager()
        setupHorizontalRecyclerView()
        setupVerticalRecyclerView()
        setupTransformer()
        healthyViewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable,4000)
            }
        })
    }

    private fun setupHorizontalRecyclerView(){
        val recipes = (activity as HomeActivity).dataManager.getRecipes()
        horizontalRecipeAdapter = HorizontalRecipeAdapter(recipes)
        binding.horizontalRecyclerView.adapter = horizontalRecipeAdapter
    }

    private fun setupVerticalRecyclerView(){
        val recipes = (activity as HomeActivity).dataManager.getRecipes()
        verticalRecipeAdapter = VerticalRecipeAdapter(recipes)
        binding.verticalRecycler.adapter = verticalRecipeAdapter
    }
    private fun setupViewPager(){
        healthyViewPager = binding.healthyViewPager
        handler = Handler(Looper.myLooper()!!)
        imageList = ArrayList()
        imageList.add(R.drawable.view_pager_image_1)
        imageList.add(R.drawable.view_pager_image_2)
        imageList.add(R.drawable.view_pager_image_3)

        imageAdapter = ImageAdapter(imageList,healthyViewPager)

        healthyViewPager.adapter = imageAdapter
        healthyViewPager.offscreenPageLimit = 3
        healthyViewPager.clipToPadding = false
        healthyViewPager.clipChildren = false
        healthyViewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
    }

    private fun setupTransformer(){
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer{ page,position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }
        healthyViewPager.setPageTransformer(transformer)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable,4000)
    }

    private val runnable = Runnable{
        healthyViewPager.currentItem = healthyViewPager.currentItem + 1
    }
}