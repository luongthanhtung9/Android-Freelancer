package com.example.freelance.ui.recycler_view

interface IOnBind<T> {
    fun onBind(data: T)
    fun onBind(data: T, payload: List<Any>)
}