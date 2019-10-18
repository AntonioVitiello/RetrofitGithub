package com.vitiello.android.retrofitgithub.network.dto

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * Created by Antonio Vitiello on 19/10/2019.
 */
data class GithubRepoData(
    @JsonProperty("id")
    val id: Int?,
    @JsonProperty("node_id")
    val nodeId: String?,
    @JsonProperty("name")
    val name: String?,
    @JsonProperty("full_name")
    val fullName: String?,
    @JsonProperty("private")
    val `private`: Boolean?,
    @JsonProperty("owner")
    val owner: Owner?,
    @JsonProperty("html_url")
    val htmlUrl: String?,
    @JsonProperty("fork")
    val fork: Boolean?,
    @JsonProperty("url")
    val url: String?,
    @JsonProperty("forks_url")
    val forksUrl: String?,
    @JsonProperty("keys_url")
    val keysUrl: String?,
    @JsonProperty("collaborators_url")
    val collaboratorsUrl: String?,
    @JsonProperty("teams_url")
    val teamsUrl: String?,
    @JsonProperty("hooks_url")
    val hooksUrl: String?,
    @JsonProperty("issue_events_url")
    val issueEventsUrl: String?,
    @JsonProperty("events_url")
    val eventsUrl: String?,
    @JsonProperty("assignees_url")
    val assigneesUrl: String?,
    @JsonProperty("branches_url")
    val branchesUrl: String?,
    @JsonProperty("tags_url")
    val tagsUrl: String?,
    @JsonProperty("blobs_url")
    val blobsUrl: String?,
    @JsonProperty("git_tags_url")
    val gitTagsUrl: String?,
    @JsonProperty("git_refs_url")
    val gitRefsUrl: String?,
    @JsonProperty("trees_url")
    val treesUrl: String?,
    @JsonProperty("statuses_url")
    val statusesUrl: String?,
    @JsonProperty("languages_url")
    val languagesUrl: String?,
    @JsonProperty("stargazers_url")
    val stargazersUrl: String?,
    @JsonProperty("contributors_url")
    val contributorsUrl: String?,
    @JsonProperty("subscribers_url")
    val subscribersUrl: String?,
    @JsonProperty("subscription_url")
    val subscriptionUrl: String?,
    @JsonProperty("commits_url")
    val commitsUrl: String?,
    @JsonProperty("git_commits_url")
    val gitCommitsUrl: String?,
    @JsonProperty("comments_url")
    val commentsUrl: String?,
    @JsonProperty("issue_comment_url")
    val issueCommentUrl: String?,
    @JsonProperty("contents_url")
    val contentsUrl: String?,
    @JsonProperty("compare_url")
    val compareUrl: String?,
    @JsonProperty("merges_url")
    val mergesUrl: String?,
    @JsonProperty("archive_url")
    val archiveUrl: String?,
    @JsonProperty("downloads_url")
    val downloadsUrl: String?,
    @JsonProperty("issues_url")
    val issuesUrl: String?,
    @JsonProperty("pulls_url")
    val pullsUrl: String?,
    @JsonProperty("milestones_url")
    val milestonesUrl: String?,
    @JsonProperty("notifications_url")
    val notificationsUrl: String?,
    @JsonProperty("labels_url")
    val labelsUrl: String?,
    @JsonProperty("releases_url")
    val releasesUrl: String?,
    @JsonProperty("deployments_url")
    val deploymentsUrl: String?,
    @JsonProperty("created_at")
    val createdAt: String?,
    @JsonProperty("updated_at")
    val updatedAt: String?,
    @JsonProperty("pushed_at")
    val pushedAt: String?,
    @JsonProperty("git_url")
    val gitUrl: String?,
    @JsonProperty("ssh_url")
    val sshUrl: String?,
    @JsonProperty("clone_url")
    val cloneUrl: String?,
    @JsonProperty("svn_url")
    val svnUrl: String?,
    @JsonProperty("size")
    val size: Int?,
    @JsonProperty("stargazers_count")
    val stargazersCount: Int?,
    @JsonProperty("watchers_count")
    val watchersCount: Int?,
    @JsonProperty("language")
    val language: String?,
    @JsonProperty("has_issues")
    val hasIssues: Boolean?,
    @JsonProperty("has_projects")
    val hasProjects: Boolean?,
    @JsonProperty("has_downloads")
    val hasDownloads: Boolean?,
    @JsonProperty("has_wiki")
    val hasWiki: Boolean?,
    @JsonProperty("has_pages")
    val hasPages: Boolean?,
    @JsonProperty("forks_count")
    val forksCount: Int?,
    @JsonProperty("archived")
    val archived: Boolean?,
    @JsonProperty("disabled")
    val disabled: Boolean?,
    @JsonProperty("open_issues_count")
    val openIssuesCount: Int?,
    @JsonProperty("forks")
    val forks: Int?,
    @JsonProperty("open_issues")
    val openIssues: Int?,
    @JsonProperty("watchers")
    val watchers: Int?,
    @JsonProperty("default_branch")
    val defaultBranch: String?,
    @JsonProperty("permissions")
    val permissions: Permissions?,
    @JsonProperty("description")
    val description: String?,
    @JsonProperty("homepage")
    val homepage: String?,
    @JsonProperty("mirror_url")
    val mirrorUrl: String?
)

data class Owner(
    @JsonProperty("login")
    val login: String?,
    @JsonProperty("id")
    val id: Int?,
    @JsonProperty("node_id")
    val nodeId: String?,
    @JsonProperty("avatar_url")
    val avatarUrl: String?,
    @JsonProperty("gravatar_id")
    val gravatarId: String?,
    @JsonProperty("url")
    val url: String?,
    @JsonProperty("html_url")
    val htmlUrl: String?,
    @JsonProperty("followers_url")
    val followersUrl: String?,
    @JsonProperty("following_url")
    val followingUrl: String?,
    @JsonProperty("gists_url")
    val gistsUrl: String?,
    @JsonProperty("starred_url")
    val starredUrl: String?,
    @JsonProperty("subscriptions_url")
    val subscriptionsUrl: String?,
    @JsonProperty("organizations_url")
    val organizationsUrl: String?,
    @JsonProperty("repos_url")
    val reposUrl: String?,
    @JsonProperty("events_url")
    val eventsUrl: String?,
    @JsonProperty("received_events_url")
    val receivedEventsUrl: String?,
    @JsonProperty("type")
    val type: String?,
    @JsonProperty("site_admin")
    val siteAdmin: Boolean?
)

data class Permissions(
    @JsonProperty("admin")
    val admin: Boolean?,
    @JsonProperty("push")
    val push: Boolean?,
    @JsonProperty("pull")
    val pull: Boolean?
)