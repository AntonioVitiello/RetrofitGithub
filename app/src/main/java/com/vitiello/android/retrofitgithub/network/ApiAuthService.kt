package com.vitiello.android.retrofitgithub.network

import com.vitiello.android.retrofitgithub.network.dto.GithubAddCommentDto
import com.vitiello.android.retrofitgithub.network.dto.GithubIssueDto
import com.vitiello.android.retrofitgithub.network.dto.GithubRepoDto
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

/**
 * Created by Antonio Vitiello on 17/10/2019.
 */
interface ApiAuthService {

    @GET("user/repos")
    fun getRepos(@Query("per_page") itemsPerPage: Int): Single<List<GithubRepoDto>>

    @GET("/repos/{owner}/{repo}/issues")
    fun getIssues(@Path("owner") owner: String, @Path("repo") repository: String): Single<List<GithubIssueDto>>

    @POST
    fun postComment(@Url url: String, @Body issue: GithubAddCommentDto): Completable

}
