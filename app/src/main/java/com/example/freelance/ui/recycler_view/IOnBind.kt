package com.vnpay.merchant.ui.recyclerview

interface IOnBind<T> {
    fun onBind(data: T)
    fun onBind(data: T, payload: List<Any>)
}