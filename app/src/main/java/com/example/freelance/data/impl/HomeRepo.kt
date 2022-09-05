package com.example.freelance.data.impl

import com.example.freelance.data.Service
import com.example.freelance.network.ResponseData

interface HomeRepo {
    suspend fun login(): ResponseData
}

class HomeRepoImpl(val service: Service) : HomeRepo{
    override suspend fun login(): ResponseData {
       return service.login()
    }

}