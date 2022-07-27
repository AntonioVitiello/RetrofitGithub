package com.vitiello.android.retrofitgithub.network.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class GithubTokenResponse(
    @JsonProperty("access_token")
    val accessToken: String?,
    @JsonProperty("scope")
    val scope: String?,
    @JsonProperty("token_type")
    val tokenType: String?
)