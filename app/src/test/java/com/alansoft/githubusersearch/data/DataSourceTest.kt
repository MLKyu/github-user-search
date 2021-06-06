package com.alansoft.githubusersearch.data

import com.alansoft.githubusersearch.data.response.Item
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test

/**
 * Created by LEE MIN KYU on 2021/06/06
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
class DataSourceTest {
    private lateinit var local: LocalDataSource

    @Before
    fun init() {
        local = LocalDataSource()
    }

    @Test
    fun insertAndLoadListTest() = runBlocking {
        val mockData = Item("이민규", 11111, "", false)
        local.pushItem("이민규", mockData)

        val loadFromLocal = local.getItems("이민규")
        MatcherAssert.assertThat(loadFromLocal.toString(), `is`(mockData.toString()))

        MatcherAssert.assertThat(local.getItems()[0].toString(), `is`(mockData.toString()))
    }
}