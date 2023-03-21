package com.cupcake.chickenmasala.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cupcake.chickenmasala.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_ChickenMasala)
        setContentView(R.layout.activity_main)
    }
}