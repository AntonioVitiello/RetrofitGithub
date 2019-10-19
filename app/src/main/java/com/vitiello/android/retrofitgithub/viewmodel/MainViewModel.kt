package com.vitiello.android.retrofitgithub.viewmodel

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vitiello.android.retrofitgithub.BuildConfig
import com.vitiello.android.retrofitgithub.model.GithubIssue
import com.vitiello.android.retrofitgithub.model.GithubRepo
import com.vitiello.android.retrofitgithub.network.GithubService
import com.vitiello.android.retrofitgithub.network.dto.GithubAddComment
import com.vitiello.android.retrofitgithub.network.map.mapGithubIssues
import com.vitiello.android.retrofitgithub.network.map.mapGithubRepos
import com.vitiello.android.retrofitgithub.tools.SingleEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by Antonio Vitiello on 17/10/2019.
 */
class MainViewModel : ViewModel() {

    var username: String = ""
    var password: String = ""
    private var mGithubService: GithubService
    private val compositeDisposable = CompositeDisposable()

    val issuesLiveData = MutableLiveData<List<GithubIssue>>()
    val repositoriesLiveData = MutableLiveData<List<GithubRepo>>()
    val commentLiveData = MutableLiveData<SingleEvent<Boolean>>()
    val networkErrorLiveData = MutableLiveData<SingleEvent<Boolean>>()
    val errorLiveData = MutableLiveData<SingleEvent<String>>()

    init {
        if (BuildConfig.DEBUG) {
            username = BuildConfig.username
            password = BuildConfig.password
        }

        val logInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val basicInterceptor = Interceptor { chain ->
            val originalRequest = chain.request()

            val builder = originalRequest.newBuilder().header(
                "Authorization",
                Credentials.basic(username, password)
            )

            val newRequest = builder.build()
            chain.proceed(newRequest)
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(basicInterceptor)
            .addInterceptor(logInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(GithubService.ENDPOINT)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        mGithubService = retrofit.create(GithubService::class.java)
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

    fun addComment(comment: String, issue: GithubIssue) {
        if (TextUtils.isEmpty(comment)) {
            errorLiveData.postValue(SingleEvent("Please enter a comment"))
            return
        } else {
            val gitComment =
                GithubAddComment(body = comment, id = issue.id!!, title = issue.title!!)
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