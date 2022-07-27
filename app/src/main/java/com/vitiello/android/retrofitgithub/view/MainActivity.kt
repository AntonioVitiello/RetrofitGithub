package com.vitiello.android.retrofitgithub.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.muddzdev.styleabletoast.StyleableToast
import com.vitiello.android.retrofitgithub.R
import com.vitiello.android.retrofitgithub.model.GithubIssueModel
import com.vitiello.android.retrofitgithub.model.GithubRepoModel
import com.vitiello.android.retrofitgithub.tools.SingleEvent
import com.vitiello.android.retrofitgithub.tools.isNotEmpty
import com.vitiello.android.retrofitgithub.view.LoginActivity.Companion.STARGAZERS_CODE
import com.vitiello.android.retrofitgithub.view.LoginActivity.Companion.STARGAZERS_SCHEME
import com.vitiello.android.retrofitgithub.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Antonio Vitiello on 17/10/2019.
 */
class MainActivity : AppCompatActivity(), CredentialsDialog.ICredentialsDialogListener {
    private val ITEMS_PER_PAGE = 100
    private val mViewModel by viewModels<MainViewModel>()
    private var mStargazersCode: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        handleIntent(intent)

        mViewModel.issuesLiveData.observe(this, Observer(::onIssuesResponse))
        mViewModel.repositoriesLiveData.observe(this, Observer(::onRepositoriesResponse))
        mViewModel.commentLiveData.observe(this, Observer(::onCommentResponse))
        mViewModel.networkErrorLiveData.observe(this, Observer(::onNetworkError))
        mViewModel.loginLiveData.observe(this, Observer(::onLogin))
        mViewModel.errorLiveData.observe(this, Observer(::onError))

        initComponents()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleIntent(intent)
    }

    private fun handleIntent(intent: Intent?) {
        intent?.data?.let { uri ->
            if (STARGAZERS_SCHEME == uri.scheme) {
                mStargazersCode = uri.getQueryParameter(STARGAZERS_CODE)
                login()
            }
        }
    }

    private fun initComponents() {
        repositoriesSpinner.isEnabled = false
        repositoriesSpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            arrayOf(getString(R.string.no_repositories))
        )

        repositoriesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                adapter: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                if (adapter.selectedItem is GithubRepoModel) {
                    val githubRepo = adapter.selectedItem as GithubRepoModel
                    isNotEmpty(
                        githubRepo.owner,
                        githubRepo.name
                    ) { owner, name ->
                        mViewModel.getIssues(owner, name)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        issuesSpinner.isEnabled = false
        issuesSpinner.adapter = ArrayAdapter(
            this@MainActivity,
            android.R.layout.simple_spinner_dropdown_item,
            arrayOf(getString(R.string.select_repository))
        )
    }

    private fun onRepositoriesResponse(repositories: List<GithubRepoModel>) {
        val spinnerAdapter = if (repositories.isEmpty()) {
            repositoriesSpinner.isEnabled = false
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                arrayOf(getString(R.string.user_no_repositories))
            )
        } else {
            repositoriesSpinner.isEnabled = true
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, repositories)
        }
        repositoriesSpinner.adapter = spinnerAdapter
    }

    private fun onCommentResponse(event: SingleEvent<Boolean>) {
        if (event.getContentIfNotHandled() == true) {
            commentEditText.setText("")
            StyleableToast.makeText(
                this,
                getString(R.string.comment_created),
                R.style.styleableToast
            ).show()
        }
    }

    private fun onIssuesResponse(issues: List<GithubIssueModel>) {
        issuesSpinner.adapter = if (issues.isEmpty()) {
            issuesSpinner.isEnabled = false
            commentEditText.isEnabled = false
            sendButton.isEnabled = false
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                arrayOf(getString(R.string.no_issues))
            )
        } else {
            issuesSpinner.isEnabled = true
            commentEditText.isEnabled = true
            sendButton.isEnabled = true
            ArrayAdapter(
                this, android.R.layout.simple_spinner_dropdown_item, issues
            )
        }
    }

    private fun onNetworkError(event: SingleEvent<Boolean>) {
        if (event.getContentIfNotHandled() == true) {
            StyleableToast.makeText(
                this,
                getString(R.string.generic_network_error),
                R.style.styleableToast
            ).show()
        }
    }

    private fun onError(event: SingleEvent<String>) {
        event.getContentIfNotHandled()?.let { exc ->
            StyleableToast.makeText(this, exc, R.style.styleableToast).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_credentials -> {
                showCredentialsDialog()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showCredentialsDialog() {
        CredentialsDialog.newInstance(mViewModel.username, mViewModel.password)
            .show(supportFragmentManager, CredentialsDialog.TAG)
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.loadReposButtons -> mViewModel.getRepositories(ITEMS_PER_PAGE)
            R.id.sendButton -> mViewModel.addComment(
                commentEditText.text.toString(),
                issuesSpinner.selectedItem as GithubIssueModel
            )
        }
    }

    override fun onDialogPositiveClick(username: String, password: String) {
        mViewModel.onCredential(username, password)
        StyleableToast.makeText(
            this,
            getString(R.string.github_oauth_msg),
            3000,
            R.style.styleableToast
        ).show()
    }

    fun login() {
        mStargazersCode?.let { stargazersCode ->
            mViewModel.login(
                getString(R.string.client_id),
                getString(R.string.client_sec),
                stargazersCode
            )
        }
    }

    private fun onLogin(event: SingleEvent<Boolean>) {
        event.getContentIfNotHandled()?.let { success ->
            loadReposButtons.isEnabled = success
        }
    }

}
