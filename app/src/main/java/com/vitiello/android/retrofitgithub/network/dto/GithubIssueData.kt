package com.vitiello.android.retrofitgithub.network.dto
import com.fasterxml.jackson.annotation.JsonProperty


/**
 * Created by Antonio Vitiello on 19/10/2019.
 */
data class GithubIssueData(
    @JsonProperty("id")
    val id: String?,
    @JsonProperty("title")
    val title: String?,
    @JsonProperty("comments_url")
    val commentsUrl: String?
)