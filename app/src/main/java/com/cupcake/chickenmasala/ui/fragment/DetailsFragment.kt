package com.cupcake.chickenmasala.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.cupcake.chickenmasala.databinding.FragmentDetailsBinding
import com.cupcake.chickenmasala.ui.base.BaseFragment
import com.cupcake.chickenmasala.data.RepositoryImpl
import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.usecase.Repository
import com.cupcake.chickenmasala.utill.DataSourceProvider


class DetailsFragment : BaseFragment<FragmentDetailsBinding>(){

    override val LOG_TAG: String = "Details_Fragment"
    private lateinit var repository: Repository
    private lateinit var ingredientsAdapter:IngredientsAdapter
    private lateinit var instructionsAdapter:InstructionsAdapter

    override val bindingInflater: (LayoutInflater, ViewGroup, Boolean) ->
    FragmentDetailsBinding = FragmentDetailsBinding::inflate
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repository = RepositoryImpl(DataSourceProvider.getDataSource(requireActivity().application))
        setupRecipeDetails(getRecipeData())


    }


    private fun setupRecipeDetails(recipe: Recipe) {
        shareLink(recipe.urlDetailsRecipe)
        loadImage(recipe.imageUrl)
        openWebsite(recipe.urlDetailsRecipe)
        setRecipeName(recipe.translatedRecipeName)
        setCuisineName(recipe.cuisine)
        setIngredientsCount(recipe.ingredientCounts)
        setTimeForRecipe(recipe.totalTimeInMin)
        setupIngredientsRecycleView(recipe.translatedIngredients)
        setupInstructionsRecycleView(recipe.translatedInstructions)
    }

    private fun getRecipeData():Recipe{
        val id = arguments.let { it?.getInt(ID) }
        val data = repository.getRecipes()
        return data[id!!]
    }


    private fun shareLink(link: String) {
        binding.shareButton.setOnClickListener {
            val sharingIntent = Intent(Intent.ACTION_SEND)
            sharingIntent.type = "text/plain"
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "check out this link!")
            sharingIntent.putExtra(Intent.EXTRA_TEXT, link)
            startActivity(Intent.createChooser(sharingIntent, "Share via"))
            setupBackButton()
        }
    }
    private fun setupBackButton() {
       binding.backButton.setOnClickListener {
           activity?.onBackPressedDispatcher?.onBackPressed()
       }
    }


    private fun openWebsite(url:String){
        binding.moreDetailsButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            activity?.startActivity(intent)
        }
    }

    private fun loadImage(imageUrl:String){
        val includedLayout = binding.p1
        val imageView = includedLayout.detailsImage
        Glide.with(this)
            .load(imageUrl)
            .into(imageView)
    }

    private fun setRecipeName(recipeName:String){
        val includedLayout = binding.p1
        val recipe = includedLayout.foodName
        recipe.text = recipeName
    }
    private fun setIngredientsCount(ingredientsCount: Int){
        val includedLayout = binding.p1
         val ingredients = includedLayout.ingredientText
         ingredients.text = ingredientsCount.toString()
    }

    private fun setCuisineName(cuisineName:String){
        val includedLayout = binding.p1
        val cuisine = includedLayout.cuisineText
        cuisine.text = cuisineName
    }


    private fun setTimeForRecipe(time:Int){
        val includedLayout = binding.p1
        val timeForRecipe = includedLayout.timerText
        timeForRecipe.text = time.toString()
    }

    private fun setupIngredientsRecycleView(ingredientsList:List<String>) {
        ingredientsAdapter = IngredientsAdapter(listOf())
        val recyclerView = binding.recyclerIngredients
        recyclerView.adapter = ingredientsAdapter
        ingredientsAdapter.setData(ingredientsList)
    }


    private fun setupInstructionsRecycleView(instructionsList:List<String>) {
        instructionsAdapter = InstructionsAdapter(listOf())
        val recyclerView = binding.recyclerInstructions
        recyclerView.adapter = instructionsAdapter
        instructionsAdapter.setData(instructionsList)
    }


    companion object{
        fun newInstance(id:Int) = DetailsFragment().apply {
            arguments = Bundle().apply {
                putInt(ID,id)
            }
        }
        const val ID = "ID"
    }




}