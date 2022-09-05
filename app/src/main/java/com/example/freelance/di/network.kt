package com.example.freelance.di

import android.content.Context
import com.example.freelance.data.Service
import com.example.freelance.network.MainRemote
import com.example.freelance.network.SSLInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

val networks = module {

    single { createOkHttpCache(get()) }
    single { createGson() }
    single { createOkHttpClient(get()) }
    factory<OkHttpClient>(named("newOkHttp"))
    {
        newBuildOkHttp(
            get(),
            60L,
            90L
        )
    }
    single { createMediaType() }
    single<Service> { createService(get(named("newOkHttp"))) }

}

fun createOkHttpClient(
    cache: Cache
): OkHttpClient {
    return OkHttpClient.Builder()
        .cache(cache)
        .build()
}

fun createOkHttpCache(context: Context): Cache {
    return Cache(context.cacheDir, Long.MAX_VALUE)
}

//fun createCertificatePinner(domain: String): CertificatePinner {
//    return CertificatePinner.Builder()
//        .apply {
//            repeat(DatasourceProperties.getCerts().size) {
//                add(domain, "sha256/${DatasourceProperties.getCerts()[it]}")
//            }
//        }
//        .build()
//}

fun newBuildOkHttp(
    client: OkHttpClient,
    timeoutConnect: Long,
    timeoutReader: Long,
): OkHttpClient {

    val log = HttpLoggingInterceptor()
    log.setLevel(HttpLoggingInterceptor.Level.BODY)

    return client.newBuilder()
        .connectTimeout(timeoutConnect, TimeUnit.SECONDS)
        .addInterceptor(SSLInterceptor())
        .addInterceptor(log)
        .connectionSpecs(listOf(ConnectionSpec.MODERN_TLS, ConnectionSpec.CLEARTEXT))
        .readTimeout(timeoutReader, TimeUnit.SECONDS).build()
}

fun createGson(): Gson {
    return GsonBuilder().create()
}

fun createMediaType(): MediaType? {
    return "application/json".toMediaTypeOrNull()
}

fun createService(client: OkHttpClient): Service {
    return MainRemote(client).getService()
}