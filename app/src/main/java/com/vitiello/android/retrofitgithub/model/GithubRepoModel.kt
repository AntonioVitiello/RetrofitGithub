package com.vitiello.android.retrofitgithub.model

/**
 * Created by Antonio Vitiello on 17/10/2019.
 */
class GithubRepoModel {
    var name: String? = null
    var owner: String? = null
    var url: String? = null

    override fun toString(): String {
        return "$name"
    }
}
