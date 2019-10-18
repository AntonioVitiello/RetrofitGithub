package com.vitiello.android.retrofitgithub

import com.google.gson.annotations.SerializedName

/**
 * Created by Antonio Vitiello on 17/10/2019.
 */
class GithubIssue {

    var id: String? = null
    var title: String? = null
    var comments_url: String? = null

    @SerializedName("body")
    internal var comment: String? = null

    override fun toString(): String {
        return "$id - $title"
    }
}
