package com.vitiello.android.retrofitgithub.network

import com.vitiello.android.retrofitgithub.network.dto.GithubTokenResponse
import io.reactivex.Single
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by Antonio Vitiello on 17/10/2019.
 */
interface ApiService {

    @Headers("Accept: application/vnd.github+json", "Content-Type: application/json")
    @POST("login/oauth/access_token")
    fun loadToken(
        @Query("client_id") idClient: String,
        @Query("client_secret") clientSecret: String,
        @Query("code") code: String
    ): Single<GithubTokenResponse>

}
