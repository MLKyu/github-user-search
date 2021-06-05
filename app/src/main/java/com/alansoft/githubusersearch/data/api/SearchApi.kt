package com.alansoft.githubusersearch.data.api

import com.alansoft.githubusersearch.data.response.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by LEE MIN KYU on 2021/06/05
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
interface SearchApi {

    /**
     * q	string	query
    The query contains one or more search keywords and qualifiers. Qualifiers allow you to limit your search to specific areas of GitHub. The REST API supports the same qualifiers as GitHub.com. To learn more about the format of the query, see Constructing a search query. See "Searching users" for a detailed list of qualifiers.

    sort	string	query
    Sorts the results of your query by number of followers or repositories, or when the person joined GitHub. Default: best match

    order	string	query
    Determines whether the first search result returned is the highest number of matches (desc) or lowest number of matches (asc). This parameter is ignored unless you provide sort.

    Default: desc
    per_page	integer	query
    Results per page (max 100).

    Default: 30
    page	integer	query
    Page number of the results to fetch.

    Default: 1

     */
    @GET("/search/users")
    suspend fun getPokemonName(
        @Query("q") q: String,
        @Query("sort") sort: String,
        @Query("order") order: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int,
    ): SearchResponse
}