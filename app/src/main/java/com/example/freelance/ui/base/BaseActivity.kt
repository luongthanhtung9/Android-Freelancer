package com.example.freelance.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity: AppCompatActivity() {

    abstract val viewModel: BaseViewModel
    protected open val binding: ViewBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (binding != null) {
            setContentView(binding?.root)
        }
        observerDefaultLiveData()
        initView()
        initListener()
        observerLiveData()

    }

    abstract fun initListener()

    abstract fun observerLiveData()

    abstract fun initView()

    private fun observerDefaultLiveData() {

    }
}