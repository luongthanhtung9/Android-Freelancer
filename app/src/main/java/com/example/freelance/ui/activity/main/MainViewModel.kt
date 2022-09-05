package com.example.freelance.ui.activity.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.freelance.data.impl.HomeRepo
import com.example.freelance.network.ResponseData
import com.example.freelance.ui.base.BaseViewModel

class MainViewModel(val homeRepo: HomeRepo): BaseViewModel() {
    private val _badgeCount = MutableLiveData<Int>()
    var number = 0
    val abc = MutableLiveData<List<ResponseData?>?>()
    val badgeCount: LiveData<Int>
        get() = _badgeCount

    fun incrementBadgeCount() {

        _badgeCount.postValue(++number)
    }

    fun getHistory() = launch {
        val response = homeRepo.login()
        response?.let {

        }
    }
}