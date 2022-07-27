package com.vitiello.android.retrofitgithub.network.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty


/**
 * Created by Antonio Vitiello on 19/10/2019.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class GithubIssueDto(
    @JsonProperty("id")
    val id: String?,
    @JsonProperty("title")
    val title: String?,
    @JsonProperty("comments_url")
    val commentsUrl: String?
)