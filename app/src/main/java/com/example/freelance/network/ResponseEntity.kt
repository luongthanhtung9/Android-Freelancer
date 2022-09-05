package com.example.freelance.network

import com.google.gson.annotations.SerializedName

abstract class ResponseEntity {
    @SerializedName("mid")
    val mid: Int = 0
    @SerializedName("code")
    val code: String? = null
    @SerializedName("des")
    val des: String? = null
    var source: String? = null

    fun isSuccess() : Boolean{
        return "00".equals(code)
    }
}

data class ResponseData(
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?
)