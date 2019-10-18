package com.vitiello.android.retrofitgithub.model

/**
 * Created by Antonio Vitiello on 17/10/2019.
 */
class GithubIssue {

    var id: String? = null
    var title: String? = null
    var commentsUrl: String? = null

    internal var comment: String? = null

    override fun toString(): String {
        return "$id - $title"
    }
}
