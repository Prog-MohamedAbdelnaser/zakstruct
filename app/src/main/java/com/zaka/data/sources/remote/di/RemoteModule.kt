package com.zaka.data.sources.remote.di

import com.google.gson.GsonBuilder
import com.zaka.data.sources.remote.RemoteConstants
import com.zaka.data.sources.remote.interceptor.ErrorHandlerInterceptor
import com.zaka.data.sources.remote.interceptor.HeadersInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.CertificateException
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

val remoteModule = module {

    val keyHeadersInterceptor = "HeadersInterceptor"
    val keyHttpLoggingInterceptor = "HttpLoggingInterceptor"
    val keyErrorHandlerInterceptor = "ErrorHandlerInterceptor"
    val keyRetrofitOkHttpClient = "RetrofitOkHttpClient"

    single<Interceptor>(StringQualifier(keyHeadersInterceptor)) { HeadersInterceptor() }

    single<Interceptor>(StringQualifier(keyHttpLoggingInterceptor)) { HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY) }

    single<Interceptor>(StringQualifier(keyErrorHandlerInterceptor)) { ErrorHandlerInterceptor(get()) }

    single<Array<TrustManager>> {
        arrayOf(object : X509TrustManager {
            @Throws(CertificateException::class)
            override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
            }

            override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                return arrayOf()
            }
        })
    }

    single { java.security.SecureRandom() }

    single<SSLContext> {
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, get<Array<TrustManager>>(), get())
        sslContext
    }

    single {
        HostnameVerifier { _, _ -> true }
    }

    single(StringQualifier(keyRetrofitOkHttpClient)) {
        OkHttpClient.Builder()
            .addInterceptor(get<Interceptor>(StringQualifier(keyHeadersInterceptor)))
            .addInterceptor(get<Interceptor>(StringQualifier(keyErrorHandlerInterceptor)))

            .addInterceptor(get<Interceptor>(StringQualifier(keyHttpLoggingInterceptor)))

            .connectTimeout(RemoteConstants.CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(RemoteConstants.READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(RemoteConstants.WRITE_TIMEOUT, TimeUnit.SECONDS)
     /*       .sslSocketFactory(
                get<SSLContext>().socketFactory,
                get<Array<TrustManager>>()[0] as X509TrustManager)*/
            .hostnameVerifier(get())
            .build()
    }

    single { GsonBuilder().serializeNulls().create() }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(RemoteConstants.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .client(get<OkHttpClient>(StringQualifier(keyRetrofitOkHttpClient)))
            .build()
    }

}