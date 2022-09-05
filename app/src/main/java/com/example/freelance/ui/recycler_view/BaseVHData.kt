package com.example.freelance.ui.recycler_view

open class BaseVHData<T>(data: T) {
    var type = 0
    var realData: T? = data
}