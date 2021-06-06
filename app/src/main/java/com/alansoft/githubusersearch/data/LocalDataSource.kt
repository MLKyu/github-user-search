package com.alansoft.githubusersearch.data

/**
 * Created by LEE MIN KYU on 2021/06/06
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
//class LocalDataSource @Inject constructor() {
//    private val cached: LinkedList<Item> = LinkedList()
//
//    fun pushSearchResponse(query: String, queryResponse: PokemonNameResponse) {
//        if (cached.size >= 5) {
//            cached.removeFirst()
//        }
//        cached.addLast(Data(query, queryResponse))
//    }
//
//    fun getSearchResponse(query: String): PokemonNameResponse {
//        return cached.first { it.query == query }.queryResponse.copy()
//    }
//
//    fun isExistAndFresh(query: String): Boolean {
//        val index = isExist(query)
//        if (index == -1) {
//            return false
//        }
//        return isFresh(index)
//    }
//
//    private fun isExist(query: String): Int {
//        return cached.indexOfFirst { it.query == query }
//    }
//
//    private fun isFresh(index: Int): Boolean {
//        if (cached[index].isFresh()) {
//            return true
//        }
//        cached.removeAt(index)
//        return false
//    }
//
//    private inner class Data(
//        val query: String,
//        val queryResponse: PokemonNameResponse
//    ) {
//        val createdAt: Long = System.currentTimeMillis()
//
//        fun isFresh(): Boolean {
//            val current = System.currentTimeMillis()
//            return (current - createdAt) < timeout
//        }
//    }
//
//    companion object {
//        private const val timeout: Long = 1 * 60 * 1000 // 1분
//    }
//
//}