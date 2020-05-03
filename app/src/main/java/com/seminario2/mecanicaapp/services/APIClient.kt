package com.seminario2.mecanicaapp.services

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.seminario2.mecanicaapp.commons.constants.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal object APIClient {
    private var retrofit: Retrofit? = null
    val clientLogin: Retrofit?
        get() {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .addInterceptor(interceptor)
                .addNetworkInterceptor(StethoInterceptor())
                .build()
            retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL_LOGIN)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit
        }

    val clientSiga: Retrofit?
        get() {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .addInterceptor(interceptor)
                .addNetworkInterceptor(StethoInterceptor())
                .build()
            retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL_SIGA)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit
        }


}