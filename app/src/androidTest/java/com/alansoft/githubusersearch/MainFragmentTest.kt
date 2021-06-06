package com.alansoft.githubusersearch

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.alansoft.githubusersearch.ui.main.PlaceholderFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by LEE MIN KYU on 2021/06/06
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MainFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Test
    fun testFragmentResultListener() {
        val scenario = launchFragmentInContainer<PlaceholderFragment>()
        scenario.onFragment { fragment ->
            val expectedResult = "result"
            fragment.parentFragmentManager.setFragmentResult(
                "requestKey",
                bundleOf("bundleKey" to expectedResult)
            )
            assertEquals(fragment.testResult, expectedResult)
        }
    }
}