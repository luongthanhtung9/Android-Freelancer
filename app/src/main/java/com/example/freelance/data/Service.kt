package com.example.freelance.data

import com.vnpay.merchant.networks.entities.ResponseData
import retrofit2.Call
import retrofit2.http.GET


interface Service {
    //    https://vietlott.vn/services/?securitycode=vietlotcmc&jsondata={"Command":"GetHistory","JsonData":""}
    @GET("/?securitycode=vietlotcmc&jsondata={\"Command\":\"GetHistory\",\"JsonData\":\"\"}")
    fun getHistory() : Call<List<ResponseData?>>?
}