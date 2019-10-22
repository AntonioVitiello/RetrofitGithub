package com.vitiello.android.retrofitgithub.network

import com.vitiello.android.retrofitgithub.network.dto.GithubAddCommentDto
import com.vitiello.android.retrofitgithub.network.dto.GithubIssueDto
import com.vitiello.android.retrofitgithub.network.dto.GithubRepoDto
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created by Antonio Vitiello on 20/10/2019.
 */
object GithubRepository {
    private lateinit var networkProvider: NetworkProvider

    fun setCredential(username: String, password: String) {
        networkProvider = NetworkProvider(username, password)
    }

    fun getRepositories(): Single<List<GithubRepoDto>> {
        return networkProvider.getRepositories()
    }

    fun getIssues(owner: String, repository: String): Single<List<GithubIssueDto>> {
        return networkProvider.getIssues(owner, repository)
    }

    fun postComment(commentUrl: String, gitComment: GithubAddCommentDto): Completable {
        return networkProvider.postComment(commentUrl, gitComment)
    }

}