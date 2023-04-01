package com.cupcake.chickenmasala.ui.fragment.home.adapter

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.cupcake.chickenmasala.R
import com.cupcake.chickenmasala.data.model.HealthAdvice
import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.databinding.ItemChipsHomeBinding
import com.cupcake.chickenmasala.databinding.ItemHorizontalRecipesBinding
import com.cupcake.chickenmasala.databinding.ItemVerticalRecipesBinding
import com.cupcake.chickenmasala.databinding.ItemViewPagerBinding
import com.cupcake.chickenmasala.ui.fragment.home.homeModel.HomeItem
import com.cupcake.chickenmasala.ui.fragment.home.homeModel.HomeItemType
import com.cupcake.chickenmasala.ui.fragment.home.HomeInteractorListener
import com.cupcake.chickenmasala.utill.setImage

class HomeRecyclerAdapter(
    private val listener: HomeInteractorListener,
    private var items: List<HomeItem<Any>>
) : RecyclerView.Adapter<HomeRecyclerAdapter.BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            HEALTHY_VIEW_PAGER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_view_pager, parent, false)
                ViewPagerViewHolder(view)
            }
            RECENT_FOOD -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_horizontal_recipes, parent, false)
                RecentFoodViewHolder(view)
            }
            CHIPS_FILTER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_chips_home, parent, false)
                ChipsViewHolder(view)
            }
            HORIZONTAL_RECIPE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_vertical_recipes, parent, false)
                FilteredFoodViewHolder(view)
            }
            else -> throw Exception(" UNKNOWN VIEW TYPE")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        when (holder) {
            is ViewPagerViewHolder -> bindViewPager(holder, position)
            is RecentFoodViewHolder -> bindRecentFood(holder, position)
            is ChipsViewHolder -> bindChipsFiltered(holder, position)
            is FilteredFoodViewHolder -> bindFilteredFood(holder, position)
        }
    }

    private fun bindChipsFiltered(holder: ChipsViewHolder, position: Int) {
        holder.binding.chipsFilter.setOnCheckedStateChangeListener { _, checkedIds ->
            listener.onChipClicked(checkedIds[0])
        }
    }

    private fun bindFilteredFood(holder: FilteredFoodViewHolder, position: Int) {
        val recipe = items[position].item as Recipe
        holder.binding.apply {
            textViewCuisineName.text = recipe.cuisine
            textViewRecipeName.text = recipe.translatedRecipeName
            textViewRecipeTime.text = recipe.totalTimeInMin.toString()
            imageViewCardImage.setImage(recipe.imageUrl)
            root.setOnClickListener {
                listener.onRecipeCardClicked(recipe.id)
            }
        }
    }

    private fun bindRecentFood(holder: RecentFoodViewHolder, position: Int) {
        val recentRecipes = items[position].item as List<Recipe>
        val horizontalAdapter =
            HorizontalRecipeRecyclerAdapter(listener)
        horizontalAdapter.submitList(recentRecipes)
        holder.binding.apply {
            recyclerViewHorizontal.adapter = horizontalAdapter
        }
    }

    private fun bindViewPager(holder: ViewPagerViewHolder, position: Int) {
        val advices = items[position].item as List<HealthAdvice>
        val viewPager = holder.binding.viewPager
        val adapter = ViewPagerAdapter(viewPager, advices, listener)
        holder.binding.apply {
            viewPager.adapter = adapter
            setupViewPager(viewPager,advices)
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

    open class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    class ViewPagerViewHolder(itemView: View) : BaseViewHolder(itemView) {
        val binding = ItemViewPagerBinding.bind(itemView)
    }
    class RecentFoodViewHolder(itemView: View) : BaseViewHolder(itemView) {
        val binding = ItemHorizontalRecipesBinding.bind(itemView)
    }
    class ChipsViewHolder(itemView: View) : BaseViewHolder(itemView) {
        val binding = ItemChipsHomeBinding.bind(itemView)
    }
    class FilteredFoodViewHolder(itemView: View) : BaseViewHolder(itemView) {
        val binding = ItemVerticalRecipesBinding.bind(itemView)
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return when (items[position].type) {
            HomeItemType.HEALTHY_VIEW_PAGER -> {
                HEALTHY_VIEW_PAGER
            }
            HomeItemType.HORIZONTAL_RECYCLER -> {
                RECENT_FOOD
            }
            HomeItemType.VERTICAL_RECYCLER -> {
                HORIZONTAL_RECIPE
            }
            HomeItemType.CHIPS_FILTERED -> {
                CHIPS_FILTER
            }
        }
    }
    fun submitList(newItems: List<HomeItem<Any>>) {
        val diffResult = DiffUtil.calculateDiff(
            AppDiffUtil(
                items,
                newItems,
            )
        )
        items = newItems
        diffResult.dispatchUpdatesTo(this)
    }

    private class AppDiffUtil<Recipe>(
        private val oldList: List<Recipe>,
        private val newList: List<Recipe>,
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldList[oldItemPosition] == newList[newItemPosition]

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]

    }

    private companion object {
        const val HEALTHY_VIEW_PAGER = 0
        const val RECENT_FOOD = 1
        const val CHIPS_FILTER = 2
        const val HORIZONTAL_RECIPE = 3
    }

}