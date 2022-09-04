package com.example.freelance.ui.activity.main

import androidx.viewbinding.ViewBinding
import com.example.freelance.databinding.ActivityMainBinding
import com.example.freelance.ui.base.BaseActivity
import com.example.freelance.ui.base.BaseViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity: BaseActivity() {
    override val viewModel: MainViewModel by viewModel()

    override val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun initListener() {

    }

    override fun observerLiveData() {

    }

    override fun initView() {

    }
}