package com.vitiello.android.retrofitgithub.network.map

import com.vitiello.android.retrofitgithub.model.GithubIssueModel
import com.vitiello.android.retrofitgithub.network.dto.GithubIssueDto
import com.vitiello.android.retrofitgithub.network.dto.GithubTokenResponse

/**
 * Created by Antonio Vitiello on 19/10/2019.
 */

fun mapGithubIssues(list: List<GithubIssueDto>): List<GithubIssueModel> {
    return ArrayList<GithubIssueModel>().apply {
        for (item in list) {
            add(GithubIssueModel().apply {
                id = item.id
                title = item.title
                commentsUrl = item.commentsUrl
            })
        }
    }
}

fun mapTokenReponse(response: GithubTokenResponse): String {
    return response.accessToken!!
}