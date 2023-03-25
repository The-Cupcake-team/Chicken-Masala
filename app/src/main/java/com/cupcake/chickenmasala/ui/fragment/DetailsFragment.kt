package com.cupcake.chickenmasala.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.cupcake.chickenmasala.data.RepositoryImpl
import com.cupcake.chickenmasala.usecase.Repository
import com.cupcake.chickenmasala.utill.DataSourceProvider

class DetailsFragment : Fragment() {

    private lateinit var repository: Repository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repository = RepositoryImpl(DataSourceProvider.getDataSource(requireActivity().application))
    }
}