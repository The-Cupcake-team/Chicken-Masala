package com.cupcake.chickenmasala.ui.fragment.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cupcake.chickenmasala.R
import com.cupcake.chickenmasala.databinding.FragmentDetailsBinding
import com.cupcake.chickenmasala.ui.base.BaseFragment
import com.cupcake.chickenmasala.data.RepositoryImpl
import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.usecase.Repository
import com.cupcake.chickenmasala.utill.DataSourceProvider
import com.cupcake.chickenmasala.utill.setImage


class DetailsFragment : BaseFragment<FragmentDetailsBinding>() {

    override val LOG_TAG: String = "Details_Fragment"
    private lateinit var repository: Repository
    private lateinit var ingredientsAdapter: IngredientsAdapter
    private lateinit var instructionsAdapter: InstructionsAdapter
    private lateinit var cleanIngredientsAdapter: CleanIngredientsAdapter

    override val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> FragmentDetailsBinding =
        FragmentDetailsBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repository = RepositoryImpl(DataSourceProvider.getDataSource(requireActivity().application))
        val id = arguments.let { it?.getInt(ID) }
        setupRecipeDetails(getRecipeById(id!!))
        setupBackButton()
    }

    private fun setupRecipeDetails(recipe: Recipe) {
        shareLink(recipe.urlDetailsRecipe)
        setDataToUiViews(recipe)
        setupIngredientsRecycleView(recipe.translatedIngredients)
        setupInstructionsRecycleView(recipe.translatedInstructions)
        setupCleanedIngredientsRecycleView(recipe.cleanedIngredients)
    }

    private fun getRecipeById(id: Int): Recipe {
        val data = repository.getRecipes()
        return data[id]
    }

    private fun shareLink(link: String) {
        binding.shareButton.setOnClickListener {
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_subject))
            sharingIntent.putExtra(Intent.EXTRA_TEXT, link)
            startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_via)))
            setupBackButton()
        }
    }


    private fun setupBackButton() {
        binding.backButton.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
    private fun setDataToUiViews(recipe: Recipe){
        binding.moreDetailsButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(recipe.imageUrl))
            startActivity(intent)
        }
        binding.container.detailsImage.setImage(recipe.imageUrl)
        binding.container.foodName.text = recipe.translatedRecipeName
        binding.container.ingredientCount.text = recipe.ingredientCounts.toString()
        binding.container.cuisineText.text = recipe.cuisine
        binding.container.timerText.text = recipe.totalTimeInMin.toString()
        binding.moreDetailsButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(recipe.urlDetailsRecipe))
            startActivity(intent)
        }

    }
    private fun setupIngredientsRecycleView(ingredientsList: List<String>) {
        ingredientsAdapter = IngredientsAdapter()
        ingredientsAdapter.submitList(ingredientsList)
        binding.recyclerIngredients.adapter = ingredientsAdapter
    }

    private fun setupInstructionsRecycleView(instructionsList: List<String>) {
        instructionsAdapter = InstructionsAdapter()
        instructionsAdapter.submitList(instructionsList)
        binding.recyclerInstructions.adapter = instructionsAdapter
    }

    private fun setupCleanedIngredientsRecycleView(cleanedIngredients: List<String>) {
        cleanIngredientsAdapter = CleanIngredientsAdapter()
        cleanIngredientsAdapter.submitList(cleanedIngredients)
        binding.container.cleanIngredientRecycle.adapter = cleanIngredientsAdapter
    }

    companion object {
        fun newInstance(id: Int) = DetailsFragment().apply {
            arguments = Bundle().apply {
                putInt(ID, id)
            }
        }

        const val ID = "ID"
    }
}