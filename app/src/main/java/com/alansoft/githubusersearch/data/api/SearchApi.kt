package com.alansoft.githubusersearch.data.api

import com.alansoft.githubusersearch.data.response.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by LEE MIN KYU on 2021/06/05
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
interface SearchApi {
    @GET("/search/users")
    suspend fun getSearchUsers(
        @Query("q") q: String,
        @Query("page") page: Int,
    ): SearchResponse
}