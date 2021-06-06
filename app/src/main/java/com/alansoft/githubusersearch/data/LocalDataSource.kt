package com.alansoft.githubusersearch.data

import com.alansoft.githubusersearch.data.response.Item
import javax.inject.Inject

/**
 * Created by LEE MIN KYU on 2021/06/06
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
class LocalDataSource @Inject constructor() {
    private val cached = mutableMapOf<String, List<Item>>()

    fun pushItem(query: String, item: Item): Boolean {
        val index = isExist(query, item)
        if (index != -1) {
            return false
        }
        add(query, item)
        return true
    }

    fun getItems(query: String? = null): List<Item> {
        return if (query == null) {
            val allItems = mutableListOf<Item>()
            cached.values.forEach {
                allItems.addAll(it.toList())
            }
            allItems.toList()
        } else {
            cached[query]?.toList() ?: listOf()
        }
    }

    private fun add(query: String, item: Item) {
        val items = cached[query]?.toMutableList() ?: mutableListOf()
        items.add(item)
        cached[query] = items
    }


    fun isExistAndFresh(item: Item): Boolean {
        val query = isExist(item) ?: return false
        return isFresh(query, item)
    }

    private fun isExist(query: String, item: Item): Int {
        return cached[query]?.indexOfFirst { it.id == item.id && it.login == item.login } ?: -1
    }

    private fun isExist(item: Item): String? {
        cached.forEach {
            val index =
                it.value.indexOfFirst { cachedItem -> cachedItem.id == item.id && cachedItem.login == item.login }

            if (index != -1) {
                return it.key
            }
        }

        return null
    }

    private fun isFresh(query: String, item: Item): Boolean {
        var returnBoolean = false
        val cacheItem = cached[query]?.toMutableList() ?: mutableListOf()
        val index =
            cacheItem.indexOfFirst { cachedItem -> cachedItem.id == item.id && cachedItem.login == item.login }
        if (index != -1) {
            cacheItem.removeAt(index)
            returnBoolean = true
        }
        cached[query] = cacheItem
        return returnBoolean
    }
}