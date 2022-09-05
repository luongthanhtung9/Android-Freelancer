package com.example.freelance.network

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONObject

class SSLInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response: Response? = null
        try {
            val requestBuilder = Request.Builder().cacheControl(CacheControl.FORCE_NETWORK)
                .headers(request.headers)
                .addHeader(HEADER_CONTENT_TYPE, HEADER_APPLICATION_JSON)
                .addHeader(HEADER_ACCEPT, HEADER_APPLICATION_JSON)
            requestBuilder.url(request.url)
            response = chain.proceed( requestBuilder.build())
            if (response.isSuccessful) {
                val responseServer = response.body?.string()
                val resJson: JSONObject? = try {
                    JSONObject(responseServer!!)
                } catch (e: Exception) {
                    null
                }
                when {
                    resJson == null -> {
                        response = response.newBuilder().code(503)
                            .addHeader("code", "503")
                            .body((responseServer
                                ?: "").toResponseBody("application/json".toMediaTypeOrNull()))
                            .build()
                    }
                    200 != response.code -> {
                        response = response.newBuilder().code(401)
                            .message(resJson.getSafeString("des"))
                            .addHeader("code", resJson.getSafeString("code"))
                            .body((responseServer
                                ?: "").toResponseBody("application/json".toMediaTypeOrNull()))
                            .build()
                    }
                    else -> {
                        response =
                            response.newBuilder().code(200)
                                .addHeader("code", "00")
                                .body((responseServer
                                    ?: "").toResponseBody("application/json".toMediaTypeOrNull()))
                                .build()
                    }
                }

            }
            return response
        }catch (ex: Exception){
            return Response.Builder()
                .code(503)
                .message("IOException")
                .addHeader("code", "IO")
                .protocol(Protocol.HTTP_1_1)
                .body("{\"code\":\"-1\"}".toResponseBody("application/json".toMediaTypeOrNull()))
                .request(
                    Request.Builder()
                        .url(getUrl())
                        .get()
                        .build()
                )
                .build()
        }


    }



    fun JSONObject.getSafeString(key: String): String {
        if (this.has(key))
            return getString(key)
        return ""
    }

    companion object {
        const val HEADER_CONTENT_TYPE= "Content-Type"
        const val HEADER_ACCEPT= "Accept"
        const val HEADER_APPLICATION_JSON = "application/json"

        fun getUrl(): String {
            return "https://dog.ceo/"
        }
    }
}