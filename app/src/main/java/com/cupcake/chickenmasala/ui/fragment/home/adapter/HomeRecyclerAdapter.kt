package com.cupcake.chickenmasala.ui.fragment.home.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.cupcake.chickenmasala.R
import com.cupcake.chickenmasala.databinding.ItemCardRecipesFoodBinding
import com.cupcake.chickenmasala.databinding.ItemChipsRecipeFoodBinding
import com.cupcake.chickenmasala.databinding.ItemRecyclerRecentFoodBinding
import com.cupcake.chickenmasala.databinding.ItemViewPagerBinding
import com.cupcake.chickenmasala.ui.fragment.home.homeModel.HomeItem
import com.cupcake.chickenmasala.ui.fragment.home.homeModel.HomeItemType
import com.cupcake.chickenmasala.ui.fragment.home.HomeInteractorListener
import com.cupcake.chickenmasala.ui.fragment.home.adapter.viewholder.ChipsViewHolder
import com.cupcake.chickenmasala.ui.fragment.home.adapter.viewholder.RecentFoodViewHolder
import com.cupcake.chickenmasala.ui.fragment.home.adapter.viewholder.RecipeFoodViewHolder
import com.cupcake.chickenmasala.ui.fragment.home.adapter.viewholder.ViewPagerViewHolder

class HomeRecyclerAdapter(
    private val listener: HomeInteractorListener,
    private var items: List<HomeItem<Any>>
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
            TYPE_RECIPE_FOOD -> {
                val view = ItemCardRecipesFoodBinding.inflate(inflater, parent, false)
                RecipeFoodViewHolder(view, listener)
            }
            else -> throw Exception(" UNKNOWN VIEW TYPE")
        }
    }

    override fun onBindViewHolder(holder: BaseHomeViewHolder, position: Int) {
        when (holder) {
            is ViewPagerViewHolder -> holder.bindItem(items[position].item)
            is RecentFoodViewHolder -> holder.bindItem(items[position].item)
            is ChipsViewHolder -> holder.bindItem(position)
            is RecipeFoodViewHolder -> holder.bindItem(items[position].item)
        }
    }


    abstract class BaseHomeViewHolder(binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        abstract fun <T> bindItem(items: T)
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return when (items[position].type) {
            HomeItemType.HEALTHY_VIEW_PAGER -> {
                TYPE_VIEW_PAGER
            }
            HomeItemType.HORIZONTAL_RECYCLER -> {
                TYPE_RECENT_FOOD
            }
            HomeItemType.VERTICAL_RECYCLER -> {
                TYPE_RECIPE_FOOD
            }
            HomeItemType.CHIPS_FILTERED -> {
                TYPE_CHIPS_RECIPE_FOOD
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