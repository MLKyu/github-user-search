package com.alansoft.githubusersearch.ui

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alansoft.githubusersearch.R
import com.alansoft.githubusersearch.Utils.BUNDLE_QUERY
import com.alansoft.githubusersearch.Utils.REQUEST_KEY
import com.alansoft.githubusersearch.Utils.TAB_TITLES
import com.alansoft.githubusersearch.databinding.ActivityMainBinding
import com.alansoft.githubusersearch.ui.main.PlaceholderFragment
import com.alansoft.githubusersearch.ui.my.MyFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.viewPager.let { viewPager ->
            viewPager.isUserInputEnabled = false
            viewPager.adapter = object : FragmentStateAdapter(this) {
                override fun createFragment(position: Int): Fragment {
                    return when (position) {
                        1 -> MyFragment.newInstance()
                        else -> PlaceholderFragment.newInstance()
                    }
                }

                override fun getItemCount(): Int {
                    return 2
                }
            }

            TabLayoutMediator(binding.tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()

            setSupportActionBar(binding.toolbar)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.let {
            menuInflater.inflate(R.menu.menu_items, it)
            setSearchMenu(it)
        }
        return super.onCreateOptionsMenu(menu)
    }

    private fun setSearchMenu(menu: Menu) {
        val searchItem = menu.findItem(R.id.action_search)
        (searchItem.actionView as SearchView).run {
            queryHint = "검색"
            setIconifiedByDefault(true)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    supportFragmentManager.setFragmentResult(
                        REQUEST_KEY,
                        bundleOf(BUNDLE_QUERY to query)
                    )
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    if (newText.isEmpty()) {
                        supportFragmentManager.setFragmentResult(
                            REQUEST_KEY,
                            bundleOf(BUNDLE_QUERY to "")
                        )
                    }
                    return true
                }
            })
            setOnCloseListener {
                // TODO
                false
            }
        }
    }
}