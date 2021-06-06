package com.alansoft.githubusersearch.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alansoft.githubusersearch.R
import com.alansoft.githubusersearch.data.response.Item
import com.alansoft.githubusersearch.databinding.ItemUserBinding

/**
 * Created by LEE MIN KYU on 2021/06/05
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
class PageAdapter(private val itemCallback: (() -> Unit)?) :
    ListAdapter<Item, ViewHolder>(AsyncDifferConfig.Builder(DiffCallback()).build()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding?.run {
            setVariable(BR.item, item)
            executePendingBindings()

            root.setOnClickListener {
                userLike.isSelected = !userLike.isSelected
                itemCallback?.invoke()
            }
        }
    }
}

class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
) {
    var binding: ItemUserBinding? = try {
        DataBindingUtil.bind(itemView)
    } catch (e: Exception) {
        null
    }
}

private class DiffCallback : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.id == newItem.id && oldItem.login == newItem.login
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem == newItem
    }
}
