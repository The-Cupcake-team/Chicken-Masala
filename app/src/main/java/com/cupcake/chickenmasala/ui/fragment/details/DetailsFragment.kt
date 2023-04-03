package com.cupcake.chickenmasala.ui.fragment.details

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cupcake.chickenmasala.R
import com.cupcake.chickenmasala.data.RepositoryImpl
import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.databinding.FragmentDetailsBinding
import com.cupcake.chickenmasala.ui.base.BaseFragment
import com.cupcake.chickenmasala.ui.fragment.details.adapter.DetailsAdapter
import com.cupcake.chickenmasala.ui.fragment.details.adapter.DetailsItem
import com.cupcake.chickenmasala.ui.fragment.details.adapter.DetailsItemType
import com.cupcake.chickenmasala.usecase.Repository
import com.cupcake.chickenmasala.usecase.core.GetRecipeByIdUseCase
import com.cupcake.chickenmasala.utill.DataSourceProvider
import com.cupcake.chickenmasala.utill.toIngredientItem
import com.cupcake.chickenmasala.utill.toInstructionItem

class DetailsFragment : BaseFragment<FragmentDetailsBinding>(), DetailsInteractorListener {

    override val LOG_TAG: String = this::class.java.name
    override val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> FragmentDetailsBinding =
        FragmentDetailsBinding::inflate

    private val repository: Repository by lazy {
        RepositoryImpl(DataSourceProvider.getDataSource(requireActivity().application))
    }
    private val getRecipeById: GetRecipeByIdUseCase by lazy {
        GetRecipeByIdUseCase(repository)
    }
    private var itemList: MutableList<DetailsItem<Any>> = mutableListOf()
    private lateinit var detailsAdapter: DetailsAdapter
    private lateinit var recipe: Recipe

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup() {
        getRecipeId()?.let { id ->
            recipe = getRecipeById(id)
            setUpRecyclerDetails(recipe)
            setUpShareButton(recipe.urlDetailsRecipe)
        }
        setupBackButton()
    }

    private fun getRecipeId(): Int? {
        return arguments.let { it?.getInt(ID) }
    }

    private fun setUpRecyclerDetails(recipe: Recipe) {
        itemList.add(DetailsItem(recipe, DetailsItemType.INFO))

        val ingredients = recipe.translatedIngredients
        itemList.addAll(ingredients.map { it.toIngredientItem() })

        detailsAdapter = DetailsAdapter(this, itemList)
        binding.recycler.adapter = detailsAdapter
    }

    private fun setUpShareButton(url: String) {
        binding.toolBar.setOnMenuItemClickListener {
            if (it.itemId == R.id.icon_share) {
                shareLink(url)
            }
            true
        }
    }

    private fun shareLink(link: String) {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_subject))
        sharingIntent.putExtra(Intent.EXTRA_TEXT, link)
        startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_via)))
        setupBackButton()
    }

    private fun setupBackButton() {
        binding.toolBar.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    companion object {
        fun newInstance(id: Int) = DetailsFragment().apply {
            arguments = Bundle().apply {
                putInt(ID, id)
            }
        }

        private const val ID = "ID"
    }

    override fun onIngredientToggleClicked() {
        itemList = itemList.take(1).toMutableList()
        val ingredients = recipe.translatedIngredients
        itemList.addAll(ingredients.map { it.toIngredientItem() })
        detailsAdapter.submitList(itemList)
    }

    override fun onInstructionToggleClicked() {
        itemList = itemList.take(1).toMutableList()
        val instructions = recipe.translatedInstructions
        itemList.addAll(instructions.map { it.toInstructionItem() })
        detailsAdapter.submitList(itemList)
    }

}