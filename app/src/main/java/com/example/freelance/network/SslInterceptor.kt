package com.example.freelance.network

import okhttp3.*
import okio.Buffer

//class SslInterceptor: Interceptor {
//    override fun intercept(chain: Interceptor.Chain): Response {
//        val request = chain.request()
//        val oldBody = request.body()
//        val buffer = Buffer()
//        oldBody!!.writeTo(buffer)
//        val strOldBody = buffer.readUtf8()
//        var response: Response? = null
//
//        val requestBuilder = Request.Builder().cacheControl(CacheControl.FORCE_NETWORK)
//            .headers(request.headers())
////            .addHeader(Tags.HEADER_CONTENT_TYPE, Tags.HEADER_APPLICATION_JSON)
////            .addHeader(Tags.HEADER_ACCEPT, Tags.HEADER_APPLICATION_JSON)
//        requestBuilder.url(request.url())
//        requestBuilder.post(RequestBody.create(,strOldBody))
//
//
//        response = chain.proceed( requestBuilder.build())
//    }
//}