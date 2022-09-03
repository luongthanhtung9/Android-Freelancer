package com.example.freelance.di

import android.content.Context
import com.example.freelance.constant.DatasourceProperties
import com.example.freelance.data.Service
import com.example.freelance.network.remote.MainRemote
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.*
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLSession
import javax.xml.datatype.DatatypeConstants.SECONDS

val networks = module {
//    single { createHostname(DatasourceProperties.getDomain()) }
//    single { createCertificatePinner(DatasourceProperties.getDomain()) }
    single { createOkHttpCache(get()) }
    single { createGson() }
    single { createOkHttpClient(get(), get(), get()) }
    factory<OkHttpClient>(named("newOkHttp"))
    {
        newBuildOkHttp(
            get(),
            DatasourceProperties.TIMEOUT_CONNECT,
            DatasourceProperties.TIMEOUT_READ
        )
    }
    single { createMediaType() }
//    single<Service> { createService(get(named("newOkHttp"))) }

}

fun createOkHttpClient(
    cache: Cache,
    hostname: HostnameVerifier,
    certificatePinner: CertificatePinner
): OkHttpClient {
    return OkHttpClient.Builder()
        .cache(cache)
        .hostnameVerifier(hostname)
        .certificatePinner(certificatePinner)
        .build()
}

fun createHostname(domain: String): HostnameVerifier {
    return VerifyHostName(domain)
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
    timeoutReader: Long
): OkHttpClient {

//    val log = HttpLoggingInterceptor()
//    log.setLevel(HttpLoggingInterceptor.Level.BODY)

    return client.newBuilder()
        .connectTimeout(timeoutConnect, TimeUnit.SECONDS)
//        .addInterceptor(SSLInterceptor(base))
//        .addInterceptor(log)
        .connectionSpecs(listOf(ConnectionSpec.MODERN_TLS, ConnectionSpec.CLEARTEXT))
        .readTimeout(timeoutReader, TimeUnit.SECONDS).build()
}

fun createGson(): Gson {
    return GsonBuilder().create()
}

fun createMediaType(): MediaType? {
    return MediaType.parse("application/json")
}


fun createService(client: OkHttpClient): Service {
    return MainRemote(client).getService()
}

class VerifyHostName(var hn: String) : HostnameVerifier {
    override fun verify(hostname: String, session: SSLSession): Boolean {
        val hv = HttpsURLConnection.getDefaultHostnameVerifier()
        return hv.verify(hn, session) && hn == hostname
    }
}
