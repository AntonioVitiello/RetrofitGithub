package com.vitiello.android.retrofitgithub.network.map

import com.vitiello.android.retrofitgithub.model.GithubRepo
import com.vitiello.android.retrofitgithub.network.dto.GithubRepoData

/**
 * Created by Antonio Vitiello on 19/10/2019.
 */

fun mapGithubRepos(list: List<GithubRepoData>): List<GithubRepo> {
    return ArrayList<GithubRepo>().apply {
        for (item in list) {
            add(GithubRepo().apply {
                name = item.name
                owner = item.owner?.login
                url = item.url
            })
        }
    }
}

