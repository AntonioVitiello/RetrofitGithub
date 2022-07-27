package com.vitiello.android.retrofitgithub.network

import android.util.Log
import com.vitiello.android.retrofitgithub.BuildConfig
import com.vitiello.android.retrofitgithub.network.dto.GithubAddCommentDto
import com.vitiello.android.retrofitgithub.network.dto.GithubIssueDto
import com.vitiello.android.retrofitgithub.network.dto.GithubRepoDto
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.plugins.RxJavaPlugins
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * Created by Antonio Vitiello on 22/10/2019.
 */
class NetworkProvider private constructor() {

    companion object {
        const val ENDPOINT = "https://api.github.com"
        private const val TAG = "NetworkProvider"
        private lateinit var apiService: ApiService
    }

    constructor(username: String, password: String) : this() {
        val httpClient = OkHttpClient.Builder().apply {
            writeTimeout(60, TimeUnit.SECONDS)
            readTimeout(60, TimeUnit.SECONDS)
            connectTimeout(60, TimeUnit.SECONDS)
            addInterceptor(BasicAuthInterceptor(username, password))
            if (BuildConfig.DEBUG) {
                addNetworkInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
            }
        }.build()

        apiService = Retrofit.Builder()
            .baseUrl(ENDPOINT)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(JacksonConverterFactory.create())
            .callbackExecutor(Executors.newCachedThreadPool())
            .client(httpClient)
            .build()
            .create(ApiService::class.java)

        //default error handler to avoid app crash
        RxJavaPlugins.setErrorHandler { t: Throwable -> Log.e(TAG, "RxJava plugin error", t) }
    }

    fun getRepositories(): Single<List<GithubRepoDto>> {
        return apiService.repos
    }

    fun getIssues(owner: String, repository: String): Single<List<GithubIssueDto>> {
        return apiService.getIssues(owner, repository)
    }

    fun postComment(commentUrl: String, gitComment: GithubAddCommentDto): Completable {
        return apiService.postComment(commentUrl, gitComment)
    }

}