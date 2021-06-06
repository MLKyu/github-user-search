package com.alansoft.githubusersearch.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alansoft.githubusersearch.R
import com.alansoft.githubusersearch.Utils.BUNDLE_QUERY
import com.alansoft.githubusersearch.Utils.REQUEST_KEY
import com.alansoft.githubusersearch.data.response.Item
import com.alansoft.githubusersearch.databinding.FragmentMainBinding
import com.alansoft.githubusersearch.ui.base.PageAdapter
import com.alansoft.githubusersearch.ui.base.PageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlaceholderFragment : Fragment() {
    private val pageViewModel: PageViewModel by activityViewModels()
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.adapter = PageAdapter(this::onItemClicked)
        binding.viewModel = pageViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFragmentResultListener(REQUEST_KEY) { key, bundle ->
            if (key == REQUEST_KEY) {
                val query = bundle.getString(BUNDLE_QUERY)
                query?.let {
                    if (it.isEmpty()) {
                        pageViewModel.setQuery("")
                        binding.adapter?.submitList(emptyList())
                    } else {
                        pageViewModel.setQuery(it)
                    }
                }
            }
        }

        setRecyclerAdapter()
    }

    private fun setRecyclerAdapter() {
        binding.recyclerView.run {
            setHasFixedSize(true)
            clearOnScrollListeners()
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val lastPosition = layoutManager.findLastVisibleItemPosition()
                    if (lastPosition == (adapter?.itemCount ?: RecyclerView.NO_POSITION) - 1) {
                        pageViewModel.loadNextPage()
                    }
                }
            })
        }
    }

    private fun onItemClicked(item: Item) {
        if (item.like) {
            pageViewModel.insertLike(item)
        } else {
            pageViewModel.deleteItem(item)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(): PlaceholderFragment {
            return PlaceholderFragment()
        }
    }
}