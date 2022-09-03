package com.vnpay.merchant.ui.recyclerview

open class BaseVHData<T>(data: T) {
    var type = 0
    var realData: T? = data
}