package com.vitiello.android.retrofitgithub.network.map

import com.vitiello.android.retrofitgithub.model.GithubIssue
import com.vitiello.android.retrofitgithub.network.dto.GithubIssueData

/**
 * Created by Antonio Vitiello on 19/10/2019.
 */

fun mapGithubIssues(list: List<GithubIssueData>): List<GithubIssue> {
    return ArrayList<GithubIssue>().apply {
        for (item in list) {
            add(GithubIssue().apply {
                id = item.id
                title = item.title
                commentsUrl = item.commentsUrl
            })
        }
    }
}

