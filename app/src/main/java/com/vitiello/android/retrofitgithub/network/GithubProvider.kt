package com.vitiello.android.retrofitgithub.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory

/**
 * Created by Antonio Vitiello on 19/10/2019.
 */
class GithubProvider private constructor() {

    lateinit var service: GithubService

    companion object {

        @Volatile
        private var INSTANCE: GithubProvider? = null

        fun getInstance(): GithubProvider? = INSTANCE
        fun getInstance(username: String, password: String): GithubProvider =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildProvider(username, password).also { INSTANCE = it }
            }

        private fun buildProvider(username: String, password: String): GithubProvider =
            GithubProvider().apply {
                val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(BasicAuthInterceptor(username, password))
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                    .build()

                val retrofit = Retrofit.Builder()
                    .baseUrl(GithubService.ENDPOINT)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                    .addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build()

                service = retrofit.create(GithubService::class.java)
            }

    }

}

