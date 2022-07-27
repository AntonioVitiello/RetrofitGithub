package com.vitiello.android.retrofitgithub.network.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty


/**
 * Created by Antonio Vitiello on 19/10/2019.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
data class GithubAddCommentDto(
    @JsonProperty("id")
    var id: String,
    @JsonProperty("title")
    var title: String,
    @JsonProperty("body")
    var body: String
)