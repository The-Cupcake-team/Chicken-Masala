package com.cupcake.chickenmasala.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.cupcake.chickenmasala.data.model.Recipe
import com.cupcake.chickenmasala.databinding.FragmentDetailsBinding
import com.cupcake.chickenmasala.ui.base.BaseFragment

class DetailsFragment : BaseFragment<FragmentDetailsBinding>(){

    override val LOG_TAG: String = "Details_Fragment"
    private lateinit var ingredientsAdapter: IngredientsAdapter
    private lateinit var instructionsAdapter: InstructionsAdapter
    override val bindingInflater: (LayoutInflater, ViewGroup, Boolean) -> FragmentDetailsBinding = FragmentDetailsBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadImage("https://lh3.googleusercontent.com/a/AGNmyxbs_jbOwkerX22XlUaYSh65EkcM4KeVPMgF5LJ0qw=s360")
        openWebsite("https://www.google.com")
        setRecipeName("mango")
        setIngredientsCount(20)
        setCuisineName("indian")
        setTimeForRecipe(45)

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

    private fun setupIngredientsRecycleView() {
        ingredientsAdapter = IngredientsAdapter(listOf())
        val recyclerView = binding.recyclerIngredients
        recyclerView.adapter = ingredientsAdapter
        val myData = arguments?.getParcelable<Recipe>("myData")
        myData?.let {
            ingredientsAdapter.setData(it.translatedIngredients)
        }
    }


    private fun setupInstructionsRecycleView() {
        instructionsAdapter = InstructionsAdapter(listOf())
        val recyclerView = binding.recyclerInstructions
        recyclerView.adapter = instructionsAdapter
        val myData = arguments?.getParcelable<Recipe>("myData")
        myData?.let {
            instructionsAdapter.setData(it.translatedInstructions)
        }
    }


    companion object{
        fun newInstance(id:Int) = DetailsFragment().apply {
            arguments = Bundle().apply {
                putInt("ID",id)
            }
        }
    }

}