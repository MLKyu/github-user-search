package com.alansoft.githubusersearch.ba

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alansoft.githubusersearch.data.Resource
import com.alansoft.githubusersearch.data.response.Item
import com.alansoft.githubusersearch.data.response.SearchResponse
import com.alansoft.githubusersearch.extension.loadWithThumbnail
import com.alansoft.githubusersearch.ui.base.PageAdapter
import com.google.android.material.textview.MaterialTextView

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
                view.visibility = View.VISIBLE
                (view.adapter as? PageAdapter)?.run {
                    submitList(response.data.items)
                }
            }
            is Resource.Empty -> {
                view.visibility = View.GONE
                (view.adapter as? PageAdapter)?.submitList(emptyList())
            }
            is Resource.Error -> {

            }
        }
    }

    @JvmStatic
    @BindingAdapter("error")
    fun bindError(view: MaterialTextView, response: Resource<SearchResponse>?) {
        when (response) {
            is Resource.Success -> {
                view.visibility = View.GONE
            }
            is Resource.Empty -> {
                view.visibility = View.VISIBLE
                view.text = "검색 결과가 없습니다."
            }
            is Resource.Error -> {
                view.visibility = View.VISIBLE
                view.text = response.exception.message
            }
        }
    }

    @JvmStatic
    @BindingAdapter("selectItem")
    fun bindSelectItem(view: RecyclerView, selectItem: Item?) {
        if (selectItem == null) {
            return
        }
        (view.adapter as? PageAdapter)?.run {
            val newList = currentList.toMutableList()
            val selectIndex =
                currentList.indexOfFirst { item -> item.id == selectItem.id && item.login == selectItem.login && item.like != selectItem.like }
            Log.d("sadfasdfasdfasdfasdf", "asdfsdfasdf $selectIndex")
            Log.d("sadfasdfasdfasdfasdf", "asdfsdfasdf $selectItem")
            if (selectIndex < 0) {
                return
            }
            newList[selectIndex] = selectItem
            submitList(newList)
        }
    }
}