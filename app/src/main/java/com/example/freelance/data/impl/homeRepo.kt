package com.example.freelance.data.impl

import com.example.freelance.data.Service
import com.vnpay.merchant.networks.entities.ResponseData

interface HomeRepo {
    suspend fun getHistory(): List<ResponseData?>?
}

class HomeRepoImpl(val service: Service): HomeRepo{
    override suspend fun getHistory(): List<ResponseData?>? {
        return service.getHistory()
    }

}