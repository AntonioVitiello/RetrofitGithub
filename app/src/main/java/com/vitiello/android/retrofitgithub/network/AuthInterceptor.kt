package com.vitiello.android.retrofitgithub.network

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by Antonio Vitiello on 27/07/2022.
 */
class AuthInterceptor(private val authToken: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
            .addHeader("Authorization", "Bearer $authToken")
            .build()
        return chain.proceed(newRequest)
    }
}