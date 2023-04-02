package com.cupcake.chickenmasala.ui.fragment.home.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.cupcake.chickenmasala.R
import com.cupcake.chickenmasala.data.model.HomeItem
import com.cupcake.chickenmasala.databinding.ItemCardRecipesFoodBinding
import com.cupcake.chickenmasala.databinding.ItemChipsRecipeFoodBinding
import com.cupcake.chickenmasala.databinding.ItemRecyclerRecentFoodBinding
import com.cupcake.chickenmasala.databinding.ItemViewPagerBinding
import com.cupcake.chickenmasala.ui.fragment.home.HomeInteractorListener
import com.cupcake.chickenmasala.ui.fragment.home.adapter.viewholder.ChipsViewHolder
import com.cupcake.chickenmasala.ui.fragment.home.adapter.viewholder.RecentFoodViewHolder
import com.cupcake.chickenmasala.ui.fragment.home.adapter.viewholder.RecipeFoodViewHolder
import com.cupcake.chickenmasala.ui.fragment.home.adapter.viewholder.ViewPagerViewHolder

class HomeRecyclerAdapter(
    private val listener: HomeInteractorListener,
    private var items: List<HomeItem> = emptyList()
) : RecyclerView.Adapter<HomeRecyclerAdapter.BaseHomeViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHomeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_VIEW_PAGER -> {
                val view = ItemViewPagerBinding.inflate(inflater, parent, false)
                ViewPagerViewHolder(view, listener)
            }
            TYPE_RECENT_FOOD -> {
                val view = ItemRecyclerRecentFoodBinding.inflate(inflater, parent, false)
                RecentFoodViewHolder(view, listener)
            }
            TYPE_CHIPS_RECIPE_FOOD -> {
                val view = ItemChipsRecipeFoodBinding.inflate(inflater, parent, false)
                ChipsViewHolder(view, listener)
            }

            else -> {
                val view = ItemCardRecipesFoodBinding.inflate(inflater, parent, false)
                RecipeFoodViewHolder(view, listener)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseHomeViewHolder, position: Int) {
        when (val item = items[position]) {
            is HomeItem.Advice -> (holder as ViewPagerViewHolder).bindItem(item.advices)
            is HomeItem.RecentFood -> (holder as RecentFoodViewHolder).bindItem(item.recent)
            is HomeItem.ChipFood -> (holder as ChipsViewHolder).bindItem(item.items)
            is HomeItem.RecipeFood -> (holder as RecipeFoodViewHolder).bindItem(item.recipe)
        }
    }


    abstract class BaseHomeViewHolder(binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        abstract fun <T> bindItem(items: T)
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is HomeItem.Advice -> TYPE_VIEW_PAGER
            is HomeItem.RecentFood -> TYPE_RECENT_FOOD
            is HomeItem.ChipFood -> TYPE_CHIPS_RECIPE_FOOD
            is HomeItem.RecipeFood -> TYPE_RECIPE_FOOD
        }
    }

    fun submitList(newItems: List<HomeItem>) {

        items = newItems.sortedBy { it.priority }

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

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]

    }

    private companion object {
        const val TYPE_VIEW_PAGER = R.layout.item_view_pager
        const val TYPE_RECENT_FOOD = R.layout.item_recycler_recent_food
        const val TYPE_CHIPS_RECIPE_FOOD = R.layout.item_chips_recipe_food
        const val TYPE_RECIPE_FOOD = R.layout.item_card_recipes_food
    }

}