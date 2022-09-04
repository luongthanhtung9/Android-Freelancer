package com.example.freelance.ui.activity.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.freelance.data.impl.HomeRepo
import com.example.freelance.ui.base.BaseViewModel

class MainViewModel(val homeRepo: HomeRepo): BaseViewModel() {
    private val _badgeCount = MutableLiveData<Int>()
    var number = 0

    val badgeCount: LiveData<Int>
        get() = _badgeCount

    fun incrementBadgeCount() {
        _badgeCount.postValue(++number)
    }
}