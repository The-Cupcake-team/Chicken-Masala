package com.cupcake.chickenmasala.ui.fragment.home.adapter.viewholder

import android.os.Handler
import android.os.Looper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.cupcake.chickenmasala.data.model.HealthAdvice
import com.cupcake.chickenmasala.databinding.ItemViewPagerBinding
import com.cupcake.chickenmasala.ui.fragment.home.HomeInteractorListener
import com.cupcake.chickenmasala.ui.fragment.home.adapter.HomeRecyclerAdapter
import com.cupcake.chickenmasala.ui.fragment.home.adapter.ViewPagerAdapter

class ViewPagerViewHolder(
    private val binding: ItemViewPagerBinding,
    private val listener: HomeInteractorListener
) :
    HomeRecyclerAdapter.BaseHomeViewHolder(binding) {
    override fun <T> bindItem(items: T) {
        val advices = items as List<HealthAdvice>
        val viewPager = binding.viewPager
        val adapter = ViewPagerAdapter(viewPager, advices, listener)
        binding.apply {
            viewPager.adapter = adapter
            setupViewPager(viewPager, advices)
            setUpTransformer(viewPager)
            textViewViewAll.setOnClickListener {
                listener.onViewAllButtonClicked()
            }
        }
    }
    private fun setupViewPager(
        viewPager: ViewPager2,
        advices: List<HealthAdvice>
    ) {
        val runnable = Runnable {
            viewPager.currentItem = viewPager.currentItem + 1
        }

        val viewPagerAdapter = ViewPagerAdapter(viewPager, advices, listener)
        viewPagerAdapter.submitList(advices)
        val handler = Handler(Looper.myLooper()!!)
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 4000)
            }
        })

        viewPager.adapter = viewPagerAdapter
        viewPager.post(runnable)
        viewPager.offscreenPageLimit = 3
        viewPager.clipToPadding = false
        viewPager.clipChildren = false
        viewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
    }

    private fun setUpTransformer(viewPager: ViewPager2) {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(20))
        transformer.addTransformer { page, position ->
            val r = 1 - Math.abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }
        viewPager.setPageTransformer(transformer)
    }

}
