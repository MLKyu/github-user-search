package com.alansoft.githubusersearch.data

import com.alansoft.githubusersearch.data.response.Item
import java.util.*
import javax.inject.Inject

/**
 * Created by LEE MIN KYU on 2021/06/06
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
class LocalDataSource @Inject constructor() {
    private val cached: LinkedList<Item> = LinkedList()

    fun pushItem(item: Item): Boolean {
        val index = isExist(item)
        if (index != -1) {
            return false
        }
        cached.addLast(item)
        return true
    }

    fun getItems(): List<Item> {
        return cached
    }

    fun isExistAndFresh(item: Item): Boolean {
        val index = isExist(item)
        if (index == -1) {
            return false
        }
        return isFresh(index)
    }

    private fun isExist(item: Item): Int {
        return cached.indexOfFirst { it.id == item.id && it.login == item.login }
    }

    private fun isFresh(index: Int): Boolean {
        cached.removeAt(index)
        return false
    }
}