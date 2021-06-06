package com.alansoft.githubusersearch.data.response

import com.google.gson.annotations.SerializedName

/**
 * Created by LEE MIN KYU on 2021/06/05
 * Copyright © 2021 Dreamus Company. All rights reserved.
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
    @SerializedName("avatar_url")
    val avatarUrl: String?,
    var like: Boolean = false
)
