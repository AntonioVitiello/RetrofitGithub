package com.vitiello.android.retrofitgithub

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by Antonio Vitiello on 17/10/2019.
 */
class MainActivity : AppCompatActivity(), CredentialsDialog.ICredentialsDialogListener {

    private lateinit var mViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        mViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        mViewModel.issuesLiveData.observe(this, Observer(::onIssuesResponse))
        mViewModel.repositoriesLiveData.observe(this, Observer(::onRepositoriesResponse))
        mViewModel.commentLiveData.observe(this, Observer(::onCommentResponse))
        mViewModel.networkErrorLiveData.observe(this, Observer(::onNetworkError))
        mViewModel.errorLiveData.observe(this, Observer(::onError))

        initComponents()

    }

    private fun initComponents() {
        repositoriesSpinner.isEnabled = false
        repositoriesSpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            arrayOf("No repositories available")
        )

        repositoriesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(
                adapter: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                if (adapter.selectedItem is GithubRepo) {
                    val githubRepo = adapter.selectedItem as GithubRepo
                    isNotEmpty(githubRepo.owner, githubRepo.name) { owner, name ->
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
            arrayOf("Please select repository")
        )
    }

    private fun onRepositoriesResponse(repositories: List<GithubRepo>) {
        val spinnerAdapter = if (repositories.isEmpty()) {
            repositoriesSpinner.isEnabled = false
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                arrayOf("User has no repositories")
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
            Toast.makeText(this, "Comment created!", Toast.LENGTH_LONG).show()
        }
    }

    private fun onIssuesResponse(issues: List<GithubIssue>) {
        issuesSpinner.adapter = if (issues.isEmpty()) {
            issuesSpinner.isEnabled = false
            commentEditText.isEnabled = false
            sendButton.isEnabled = false
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                arrayOf("Repository has no issues")
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
            Toast.makeText(
                this,
                "Generic Network Error, please try again later.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun onError(event: SingleEvent<String>) {
        event.getContentIfNotHandled()?.let {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
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
            .show(supportFragmentManager, "credentialsDialog")
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.loadReposButtons -> mViewModel.loadRepositories()
            R.id.sendButton -> mViewModel.addComment(
                commentEditText.text.toString(),
                issuesSpinner.selectedItem as GithubIssue
            )
        }
    }

    override fun onDialogPositiveClick(username: String, password: String) {
        mViewModel.username = username
        mViewModel.password = password
        loadReposButtons.isEnabled = true
    }

}
