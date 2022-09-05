package com.example.freelance.data


import com.example.freelance.network.ResponseData
import retrofit2.http.*

interface Service {

    @GET("api/breeds/image/random")
    suspend fun login() : ResponseData

}
