package com.vitiello.android.retrofitgithub

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.*

/**
 * Created by Antonio Vitiello on 17/10/2019.
 */
interface GithubService {

    @get:GET("user/repos?per_page=100")
    val repos: Single<List<GithubRepo>>

    @GET("/repos/{owner}/{repo}/issues")
    fun getIssues(@Path("owner") owner: String, @Path("repo") repository: String): Single<List<GithubIssue>>

    @POST
    fun postComment(@Url url: String, @Body issue: GithubIssue): Single<ResponseBody>

    companion object {
        val ENDPOINT = "https://api.github.com"
    }
}
