package com.vitiello.android.retrofitgithub.viewmodel

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vitiello.android.retrofitgithub.App
import com.vitiello.android.retrofitgithub.BuildConfig
import com.vitiello.android.retrofitgithub.R
import com.vitiello.android.retrofitgithub.model.GithubIssueModel
import com.vitiello.android.retrofitgithub.model.GithubRepoModel
import com.vitiello.android.retrofitgithub.network.GithubRepository
import com.vitiello.android.retrofitgithub.network.dto.GithubAddCommentDto
import com.vitiello.android.retrofitgithub.network.map.mapGithubIssues
import com.vitiello.android.retrofitgithub.network.map.mapGithubRepos
import com.vitiello.android.retrofitgithub.network.map.mapTokenReponse
import com.vitiello.android.retrofitgithub.tools.SingleEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


/**
 * Created by Antonio Vitiello on 17/10/2019.
 */
class MainViewModel : ViewModel() {

    var username: String
        private set
    var password: String
        private set

    private val TAG = "MainViewModel"
    private val compositeDisposable = CompositeDisposable()
    val issuesLiveData = MutableLiveData<List<GithubIssueModel>>()
    val repositoriesLiveData = MutableLiveData<List<GithubRepoModel>>()
    val commentLiveData = MutableLiveData<SingleEvent<Boolean>>()
    val networkErrorLiveData = MutableLiveData<SingleEvent<Boolean>>()
    val loginLiveData = MutableLiveData<SingleEvent<Boolean>>()
    val errorLiveData = MutableLiveData<SingleEvent<String>>()

    init {
        username = if (BuildConfig.username != "null") BuildConfig.username else ""
        password = if (BuildConfig.password != "null") BuildConfig.password else ""
    }

    fun login(idClient: String, clientSecret: String, stargazersCode: String) {
        compositeDisposable.add(
            GithubRepository.loadTokenSingle(idClient, clientSecret, stargazersCode)
                .subscribeOn(Schedulers.io())
                //.observeOn(AndroidSchedulers.mainThread())
                .map(::mapTokenReponse)
                .subscribe({ token ->
                    GithubRepository.setAuthToken(token)
                    loginLiveData.postValue(SingleEvent(true))
                }, { exc ->
                    networkErrorLiveData.postValue(SingleEvent(true))
                    Log.e(TAG, "Login Error", exc)
                })
        )
    }

    fun onCredential(username: String, password: String) {
        this.username = username
        this.password = password
        GithubRepository.setCredential(username, password)
    }

    fun getRepositories(itemsPerPage: Int) {
        compositeDisposable.add(
            GithubRepository.getRepositories(itemsPerPage)
                .subscribeOn(Schedulers.io())
                //.observeOn(AndroidSchedulers.mainThread())
                .map(::mapGithubRepos)
                .subscribe({ repos ->
                    repositoriesLiveData.postValue(repos)
                }, { exc ->
                    networkErrorLiveData.postValue(SingleEvent(true))
                    exc.printStackTrace()
                })
        )
    }

    fun getIssues(owner: String, repository: String) {
        compositeDisposable.add(
            GithubRepository.getIssues(owner, repository)
                .subscribeOn(Schedulers.io())
                //.observeOn(AndroidSchedulers.mainThread())
                .map(::mapGithubIssues)
                .subscribe({ issues ->
                    issuesLiveData.postValue(issues)
                }, { exc ->
                    networkErrorLiveData.postValue(
                        SingleEvent(
                            true
                        )
                    )
                    exc.printStackTrace()
                })
        )
    }

    fun addComment(comment: String, issue: GithubIssueModel) {
        if (TextUtils.isEmpty(comment)) {
            errorLiveData.postValue(SingleEvent(App.getString(R.string.enter_comment)))
            return
        } else {
            val gitComment =
                GithubAddCommentDto(body = comment, id = issue.id!!, title = issue.title!!)
            issue.comment = comment
            issue.commentsUrl?.let { url ->
                compositeDisposable.add(
                    GithubRepository.postComment(url, gitComment)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            commentLiveData.postValue(SingleEvent(true))
                        }, { exc ->
                            networkErrorLiveData.postValue(
                                SingleEvent(
                                    true
                                )
                            )
                            exc.printStackTrace()
                        })
                )
            }
                ?: errorLiveData.postValue(SingleEvent(App.getString(R.string.undefined_comment_url)))
        }
    }

    override fun onCleared() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

}