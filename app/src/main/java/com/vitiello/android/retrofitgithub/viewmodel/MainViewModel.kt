package com.vitiello.android.retrofitgithub.viewmodel

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vitiello.android.retrofitgithub.BuildConfig
import com.vitiello.android.retrofitgithub.model.GithubIssueModel
import com.vitiello.android.retrofitgithub.model.GithubRepoModel
import com.vitiello.android.retrofitgithub.network.GithubProvider
import com.vitiello.android.retrofitgithub.network.GithubService
import com.vitiello.android.retrofitgithub.network.dto.GithubAddCommentDto
import com.vitiello.android.retrofitgithub.network.map.mapGithubIssues
import com.vitiello.android.retrofitgithub.network.map.mapGithubRepos
import com.vitiello.android.retrofitgithub.tools.SingleEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


/**
 * Created by Antonio Vitiello on 17/10/2019.
 */
class MainViewModel : ViewModel() {

    var username: String = BuildConfig.username
        private set
    var password: String = BuildConfig.password
        private set
    private lateinit var mGithubService: GithubService
    private val compositeDisposable = CompositeDisposable()

    val issuesLiveData = MutableLiveData<List<GithubIssueModel>>()
    val repositoriesLiveData = MutableLiveData<List<GithubRepoModel>>()
    val commentLiveData = MutableLiveData<SingleEvent<Boolean>>()
    val networkErrorLiveData = MutableLiveData<SingleEvent<Boolean>>()
    val errorLiveData = MutableLiveData<SingleEvent<String>>()

    fun init(username: String, password: String) {
        this.username = username
        this.password = password
        mGithubService = GithubProvider.getInstance(username, password).service
    }

    fun getRepositories() {
        compositeDisposable.add(
            mGithubService.repos
                .subscribeOn(Schedulers.io())
                //.observeOn(AndroidSchedulers.mainThread())
                .map(::mapGithubRepos)
                .subscribe({ repos ->
                    repositoriesLiveData.postValue(repos)
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

    fun getIssues(owner: String, name: String) {
        compositeDisposable.add(
            mGithubService.getIssues(owner, name)
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
            errorLiveData.postValue(SingleEvent("Please enter a comment"))
            return
        } else {
            val gitComment =
                GithubAddCommentDto(body = comment, id = issue.id!!, title = issue.title!!)
            issue.comment = comment
            issue.commentsUrl?.let {
                compositeDisposable.add(
                    mGithubService.postComment(it, gitComment)
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
                ?: errorLiveData.postValue(SingleEvent("Undefined comment url, please try again later"))
        }
    }

    override fun onCleared() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

}