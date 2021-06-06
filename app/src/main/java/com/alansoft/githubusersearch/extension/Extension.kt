package com.alansoft.githubusersearch.extension

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import com.alansoft.githubusersearch.data.request.SearchRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

/**
 * Created by LEE MIN KYU on 2021/06/05
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
fun ImageView.loadWithThumbnail(uri: String?) {
    Glide.with(context)
        .load(uri)
        .thumbnail(0.1f)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

infix fun String.to(that: Int): SearchRequest = SearchRequest(this, that)