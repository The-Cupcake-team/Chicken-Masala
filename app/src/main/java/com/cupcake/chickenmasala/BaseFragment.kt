package com.cupcake.chickenmasala

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding> : Fragment() {
    abstract val LOG_TAG: String
    abstract val bindingInflater:(LayoutInflater,ViewGroup , Bundle )-> VB
    private var _binding: ViewBinding? = null
    protected var binding = _binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return bindingInflater(inflater,container!!,savedInstanceState!!).root
    }
    abstract fun setup()
    abstract fun addCallbacks()
    protected fun log(value: String ){
        Log.v(LOG_TAG,value)
    }

}