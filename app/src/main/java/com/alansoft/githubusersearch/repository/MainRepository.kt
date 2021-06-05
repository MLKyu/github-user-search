package com.alansoft.githubusersearch.repository

import androidx.annotation.WorkerThread
import com.alansoft.githubusersearch.data.Resource
import com.alansoft.githubusersearch.data.SearchDataSource
import com.alansoft.githubusersearch.data.request.SearchRequest
import com.alansoft.githubusersearch.data.response.SearchResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import java.io.IOException
import javax.inject.Inject

/**
 * Created by LEE MIN KYU on 2021/06/05
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
class MainRepository @Inject constructor(
    private val remote: SearchDataSource
) {
    @WorkerThread
    fun getSearchUsers(
        request: SearchRequest
    ): Flow<Resource<SearchResponse>> = flow {
        val response = remote.getSearchUsers(request)
        if (response.items.isNullOrEmpty()) {
            emit(Resource.empty())
        } else {
            emit(Resource.success(response))
        }
    }.onStart {
    }.retry(2) { cause ->
        cause is IOException
    }.catch { e ->
        emit(Resource.error(e))
    }.onCompletion {
    }.flowOn(Dispatchers.IO)
}