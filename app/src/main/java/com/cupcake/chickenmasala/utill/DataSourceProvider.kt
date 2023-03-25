package com.cupcake.chickenmasala.utill

import android.app.Application
import com.cupcake.chickenmasala.data.data_sourse.DataSource
import com.cupcake.chickenmasala.data.data_sourse.DataSourceImpl

class DataSourceProvider {

    companion object {
        private var INSTANCE: DataSource? = null
        fun getDataSource(context: Application): DataSource {
            return INSTANCE ?: DataSourceImpl(context).also {
                INSTANCE = it
            }
        }
    }
}