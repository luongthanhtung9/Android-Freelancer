package com.vnpay.merchant.networks.entities
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

class ResponseData : ResponseEntity(){
    @SerializedName("HistoryId")
    val HistoryId: String? = null
    @SerializedName("HistoryDate")
    val HistoryDate: String? = null
    @SerializedName("Subject")
    val Subject: String? = null

}