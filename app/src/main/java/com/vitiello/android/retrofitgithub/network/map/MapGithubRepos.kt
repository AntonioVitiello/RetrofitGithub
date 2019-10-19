package com.vitiello.android.retrofitgithub.network.map

import com.vitiello.android.retrofitgithub.model.GithubRepoModel
import com.vitiello.android.retrofitgithub.network.dto.GithubRepoDto

/**
 * Created by Antonio Vitiello on 19/10/2019.
 */

fun mapGithubRepos(list: List<GithubRepoDto>): List<GithubRepoModel> {
    return ArrayList<GithubRepoModel>().apply {
        for (item in list) {
            add(GithubRepoModel().apply {
                name = item.name
                owner = item.owner?.login
                url = item.url
            })
        }
    }
}

