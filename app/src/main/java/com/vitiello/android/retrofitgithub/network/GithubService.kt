package com.vitiello.android.retrofitgithub.network

import com.vitiello.android.retrofitgithub.model.GithubIssue
import com.vitiello.android.retrofitgithub.network.dto.GithubIssueData
import com.vitiello.android.retrofitgithub.network.dto.GithubRepoData
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

/**
 * Created by Antonio Vitiello on 17/10/2019.
 */
interface GithubService {

    @get:GET("user/repos?per_page=100")
    val repos: Single<List<GithubRepoData>>

    @GET("/repos/{owner}/{repo}/issues")
    fun getIssues(@Path("owner") owner: String, @Path("repo") repository: String): Single<List<GithubIssueData>>

    @POST
    fun postComment(@Url url: String, @Body issue: GithubIssue): Completable

    companion object {
        val ENDPOINT = "https://api.github.com"
    }
}
