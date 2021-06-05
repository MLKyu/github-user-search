package com.alansoft.githubusersearch.ba

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.alansoft.githubusersearch.extension.loadWithThumbnail

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
}