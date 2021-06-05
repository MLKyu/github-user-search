package com.alansoft.githubusersearch.data.response

import com.google.gson.annotations.SerializedName

/**
 * Created by LEE MIN KYU on 2021/06/05
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
/**
 * {
"total_count": 12,
"incomplete_results": false,
"items": [
{
"login": "mojombo",
"id": 1,
"node_id": "MDQ6VXNlcjE=",
"avatar_url": "https://secure.gravatar.com/avatar/25c7c18223fb42a4c6ae1c8db6f50f9b?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png",
"gravatar_id": "",
"url": "https://api.github.com/users/mojombo",
"html_url": "https://github.com/mojombo",
"followers_url": "https://api.github.com/users/mojombo/followers",
"subscriptions_url": "https://api.github.com/users/mojombo/subscriptions",
"organizations_url": "https://api.github.com/users/mojombo/orgs",
"repos_url": "https://api.github.com/users/mojombo/repos",
"received_events_url": "https://api.github.com/users/mojombo/received_events",
"type": "User",
"score": 1,
"following_url": "https://api.github.com/users/mojombo/following{/other_user}",
"gists_url": "https://api.github.com/users/mojombo/gists{/gist_id}",
"starred_url": "https://api.github.com/users/mojombo/starred{/owner}{/repo}",
"events_url": "https://api.github.com/users/mojombo/events{/privacy}",
"site_admin": true
}
]
}
 */
data class SearchResponse(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    val items: List<Item>?
)

data class Item(
    val login: String?,
    val id: Long,
    @SerializedName("node_id")
    val nodeId: String?,
    @SerializedName("avatar_url")
    val avatarUrl: String?,
    @SerializedName("gravatar_id")
    val gravatarId: String?
)
