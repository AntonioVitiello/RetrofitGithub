package com.vitiello.android.retrofitgithub.network.dto
import com.fasterxml.jackson.annotation.JsonProperty


/**
 * Created by Antonio Vitiello on 19/10/2019.
 */
data class GithubAddCommentDto(
    @JsonProperty("id")
    var id: String,
    @JsonProperty("title")
    var title: String,
    @JsonProperty("body")
    var body: String
)