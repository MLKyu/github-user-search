package com.alansoft.githubusersearch.ui.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.alansoft.githubusersearch.R
import com.alansoft.githubusersearch.data.response.Item
import com.alansoft.githubusersearch.databinding.FragmentMyBinding
import com.alansoft.githubusersearch.ui.base.PageAdapter
import com.alansoft.githubusersearch.ui.base.PageViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by LEE MIN KYU on 2021/06/06
 * Copyright © 2021 Dreamus Company. All rights reserved.
 */
@AndroidEntryPoint
class MyFragment : Fragment() {
    private val viewModel: PageViewModel by activityViewModels()
    private lateinit var binding: FragmentMyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.adapter = PageAdapter(this::onItemClicked)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadLocal()
    }

    private fun onItemClicked(item: Item) {
        viewModel.deleteItem(item)
    }

    companion object {
        @JvmStatic
        fun newInstance() = MyFragment()
    }
}