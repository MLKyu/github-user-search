package com.alansoft.githubusersearch.repository

import androidx.annotation.WorkerThread
import com.alansoft.githubusersearch.data.LocalDataSource
import com.alansoft.githubusersearch.data.Resource
import com.alansoft.githubusersearch.data.SearchDataSource
import com.alansoft.githubusersearch.data.request.SearchRequest
import com.alansoft.githubusersearch.data.response.Item
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
    private val remote: SearchDataSource,
    private val local: LocalDataSource
) {

    @WorkerThread
    fun getSearchUsers(
        request: SearchRequest
    ): Flow<Resource<SearchResponse>> = flow {
        val response = remote.getSearchUsers(request)
        if (response.items.isNullOrEmpty()) {
            emit(Resource.empty())
        } else {
            if (!local.getItems().isNullOrEmpty()) {
                local.getItems().forEach { localItme ->
                    for (item in response.items) {
                        if (localItme.id == item.id) {
                            item.like = true
                            break
                        }
                    }
                }
            }
            emit(Resource.success(response))
        }
    }.retry(2) { cause ->
        cause is IOException
    }.catch { e ->
        emit(Resource.error(e))
    }.flowOn(Dispatchers.IO)

    fun insertItem(item: Item) = local.pushItem(item)

    @WorkerThread
    fun getLocalItems(query: String? = null) = flow {
        val local = if (query == null) {
            local.getItems()
        } else {
            local.getItems().filter { it.login?.contains(query) ?: false }
        }

        if (local.isNullOrEmpty()) {
            emit(Resource.empty())
        } else {
            emit(Resource.success(SearchResponse(local.size, false, local)))
        }
    }.flowOn(Dispatchers.IO)

    fun deleteItem(item: Item) = local.isExistAndFresh(item)
}