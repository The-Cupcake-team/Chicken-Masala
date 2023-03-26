package com.cupcake.chickenmasala.ui.fragment.home


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.cupcake.chickenmasala.data.model.HealthAdvice
import com.cupcake.chickenmasala.databinding.SliderImageContainerBinding
import com.cupcake.chickenmasala.ui.base.BaseAdapter
import com.cupcake.chickenmasala.utill.setImage

class ViewPagerAdapter(
    private val viewPager: ViewPager2,
    private val advices: List<HealthAdvice>
) : BaseAdapter<HealthAdvice, SliderImageContainerBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> SliderImageContainerBinding =
        SliderImageContainerBinding::inflate

    override fun bindItem(binding: SliderImageContainerBinding, item: HealthAdvice) {
        binding.apply {
            viewPager.post(runnable)
            healthImage.setImage(item.imageUrl)
            adviceName.text = item.title
        }
    }

    private val runnable = Runnable {
        advices.toMutableList().addAll(advices)
    }

    override fun <T> areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int,
        newItems: List<T>
    ) = getOldItems()[oldItemPosition].title == (newItems[newItemPosition] as HealthAdvice).title
}
