package com.cupcake.chickenmasala.ui

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
import com.cupcake.chickenmasala.BaseFragment
import com.cupcake.chickenmasala.R
import com.cupcake.chickenmasala.databinding.FragmentHomeBinding
import com.cupcake.chickenmasala.utill.ImageAdapter
import java.lang.Math.abs

class HomeFragment: BaseFragment<FragmentHomeBinding>() {
    override val LOG_TAG: String
        get() = "HOME_FRAGMENT"
    override val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    private lateinit var viewPager2: ViewPager2
    private lateinit var handler: Handler
    private lateinit var imageList: ArrayList<Int>
    private lateinit var adapter: ImageAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init()
        setUpTransformer()
        viewPager2.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable,4000)
            }
        })
        super.onViewCreated(view, savedInstanceState)
    }

    private fun init(){
        viewPager2 = binding.healthyViewPager
        handler = Handler(Looper.myLooper()!!)
        imageList = ArrayList()
        imageList.add(R.drawable.food_image)
        imageList.add(R.drawable.food_image_2)

        adapter = ImageAdapter(imageList,viewPager2)

        viewPager2.adapter = adapter
        viewPager2.offscreenPageLimit = 3
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
    }

    private fun setUpTransformer(){
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer{ page,position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }
        viewPager2.setPageTransformer(transformer)
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
        viewPager2.currentItem = viewPager2.currentItem + 1
    }
}