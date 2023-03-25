package com.cupcake.chickenmasala.utill

import android.app.Application
import com.cupcake.chickenmasala.data.RepositoryImpl
import com.cupcake.chickenmasala.data.data_sourse.DataSourceImpl

class RepositoryProvider private constructor(private val context: Application) {

    fun getRepo() =  RepositoryImpl(DataSourceImpl(context))

    companion object {
        private var INSTANCE: RepositoryProvider? = null
        fun getInstance(context: Application): RepositoryProvider {
            return INSTANCE ?: RepositoryProvider(context).also {
                INSTANCE = it
            }
        }
    }
}