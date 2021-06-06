package com.alansoft.githubusersearch.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alansoft.githubusersearch.R
import com.alansoft.githubusersearch.Utils.BUNDLE_QUERY
import com.alansoft.githubusersearch.Utils.REQUEST_KEY
import com.alansoft.githubusersearch.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlaceholderFragment : Fragment() {
    private lateinit var pageViewModel: PageViewModel
    private lateinit var binding: FragmentMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

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
                        binding.adapter?.submitList(null)
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

    private fun onItemClicked() {

    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

        @JvmStatic
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}