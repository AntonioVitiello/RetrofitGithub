package com.vitiello.android.retrofitgithub.network

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Created by Antonio Vitiello on 19/10/2019.
 */
class BasicAuthInterceptor(user: String, password: String) : Interceptor {

    private val credentials: String

    init {
        credentials = Credentials.basic(user, password)
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authenticatedRequest = request.newBuilder()
            .header("Authorization", credentials)
            .build()
        return chain.proceed(authenticatedRequest)
    }
}