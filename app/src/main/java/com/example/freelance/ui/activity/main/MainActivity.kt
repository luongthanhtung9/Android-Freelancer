package com.example.freelance.ui.activity.main

import android.app.PendingIntent.getService
import android.util.Log
import android.view.View
import androidx.viewbinding.ViewBinding
import com.example.freelance.data.Service
import com.example.freelance.databinding.ActivityMainBinding
import com.example.freelance.network.remote.MainRemote
import com.example.freelance.ui.base.BaseActivity
import com.example.freelance.ui.base.BaseViewModel
import com.vnpay.merchant.utils.extensions.setSafeOnClickListener
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {
    override val viewModel: MainViewModel by viewModel()

    override val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    val mainRemote : MainRemote by inject()

    override fun initListener() {
        binding.btn.setSafeOnClickListener {
//            viewModel.incrementBadgeCount()
            mainRemote.getService().getHistory()
        }
    }

    override fun observerLiveData() {
        viewModel.badgeCount.observe(this) {
            binding.txt.text = it.toString()
        }
        viewModel.apply {
            abc.observe(this@MainActivity) {
                binding.txt.text = it.toString()
            }
        }
    }

    override fun initView() {
        binding.btn.text = "Cộng"
//    Service.getHistory()
//        MainRemote.getServic.getNews.enqueue(this)
    }
}