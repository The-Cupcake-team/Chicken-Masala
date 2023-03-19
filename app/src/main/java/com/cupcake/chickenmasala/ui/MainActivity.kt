package com.cupcake.chickenmasala.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cupcake.chickenmasala.R
import com.cupcake.chickenmasala.data.domain.Food
import com.cupcake.chickenmasala.utill.CsvParser

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}