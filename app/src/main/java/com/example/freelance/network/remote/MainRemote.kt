package com.example.freelance.network.remote

import com.example.freelance.constant.DatasourceProperties
import com.example.freelance.data.Service
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainRemote(client: OkHttpClient) {
    private val service: Service

    init {
        val gson = GsonBuilder()
            .setLenient()
            .disableHtmlEscaping()
            .create()
        service = Retrofit.Builder()
            .baseUrl(DatasourceProperties.getUrl())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build().create(
                Service::class.java
            )
    }

    fun getService(): Service {
        return service
    }
}