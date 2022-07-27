package com.vitiello.android.retrofitgithub.network

import com.vitiello.android.retrofitgithub.network.dto.GithubAddCommentDto
import com.vitiello.android.retrofitgithub.network.dto.GithubIssueDto
import com.vitiello.android.retrofitgithub.network.dto.GithubRepoDto
import com.vitiello.android.retrofitgithub.network.dto.GithubTokenResponse
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created by Antonio Vitiello on 20/10/2019.
 */
object GithubRepository {
    private val mNetworkProvider = NetworkProvider()

    fun setAuthToken(authToken: String) {
        mNetworkProvider.setAuthToken(authToken)
    }

    fun setCredential(username: String, password: String) {
        mNetworkProvider.setCredential(username, password)
    }

    fun loadTokenSingle(idClient: String, clientSecret: String, code: String): Single<GithubTokenResponse> {
        return mNetworkProvider.loadTokenSingle(idClient, clientSecret, code)
    }

    fun getRepositories(itemsPerPage: Int): Single<List<GithubRepoDto>> {
        return mNetworkProvider.getRepositories(itemsPerPage)
    }

    fun getIssues(owner: String, repository: String): Single<List<GithubIssueDto>> {
        return mNetworkProvider.getIssues(owner, repository)
    }

    fun postComment(commentUrl: String, gitComment: GithubAddCommentDto): Completable {
        return mNetworkProvider.postComment(commentUrl, gitComment)
    }

}