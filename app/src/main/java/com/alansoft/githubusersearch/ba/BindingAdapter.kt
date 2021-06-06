package com.alansoft.githubusersearch.ba

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alansoft.githubusersearch.data.Resource
import com.alansoft.githubusersearch.data.response.SearchResponse
import com.alansoft.githubusersearch.extension.loadWithThumbnail
import com.alansoft.githubusersearch.ui.main.PageAdapter

/**
 * Created by LEE MIN KYU on 2021/06/05
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
object BindingAdapter {
    @JvmStatic
    @BindingAdapter("loadImg")
    fun bindLaodImg(view: ImageView, url: String) {
        view.loadWithThumbnail(url)
    }

    @JvmStatic
    @BindingAdapter("adapter")
    fun bindAdapter(view: RecyclerView, adapter: PageAdapter) {
        view.adapter = adapter
    }

    @JvmStatic
    @BindingAdapter("submitList")
    fun bindSubmitList(view: RecyclerView, response: Resource<SearchResponse>?) {
        when (response) {
            is Resource.Success -> {
                (view.adapter as? PageAdapter)?.submitList(response.data.items)
            }
            else -> {

            }
        }

    }
}