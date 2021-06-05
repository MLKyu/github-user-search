package com.alansoft.githubusersearch.data

import com.alansoft.githubusersearch.data.api.SearchApi
import com.alansoft.githubusersearch.data.request.SearchRequest
import com.alansoft.githubusersearch.data.response.SearchResponse
import javax.inject.Inject

/**
 * Created by LEE MIN KYU on 2021/06/05
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
class SearchDataSource @Inject constructor(private val searchApi: SearchApi) {
    suspend fun getSearchUsers(request: SearchRequest): SearchResponse {
        return searchApi.getSearchUsers(request.query, request.page)
    }
}