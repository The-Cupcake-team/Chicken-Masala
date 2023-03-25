package com.cupcake.chickenmasala.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.cupcake.chickenmasala.utill.RepositoryProvider

class DetailsFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Don't make this instance of repo as a global variable
        // just pass the repo to the functions that need repo
        // Don't forget delete this comment
        val repository = RepositoryProvider.getInstance(requireActivity().application).getRepo()
    }
}