package com.alansoft.githubusersearch.ui.main

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alansoft.githubusersearch.R
import com.alansoft.githubusersearch.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * A placeholder fragment containing a simple view.
 */
@AndroidEntryPoint
class PlaceholderFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val adapter: PageAdapter = PageAdapter(this::onItemClicked)
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
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        setRecyclerAdapter()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_items, menu)
        setSearchMenu(menu)
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
//                        viewModel.loadNextPage()
                    }
                }
            })
            adapter = this@PlaceholderFragment.adapter
        }
    }

    private fun setSearchMenu(menu: Menu) {
        val searchItem = menu.findItem(R.id.action_search)
        (searchItem.actionView as SearchView).run {
            queryHint = "검색"
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    return true
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}