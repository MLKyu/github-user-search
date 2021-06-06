package com.alansoft.githubusersearch.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.alansoft.githubusersearch.Utils.FIRST_PAGE
import com.alansoft.githubusersearch.data.Resource
import com.alansoft.githubusersearch.data.request.SearchRequest
import com.alansoft.githubusersearch.extension.to
import com.alansoft.githubusersearch.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PageViewModel @Inject constructor(
    repository: MainRepository
) : ViewModel() {

    private val _index = MutableLiveData<Int>()
    val index: LiveData<Int> = _index

    private val query: MutableStateFlow<SearchRequest> = MutableStateFlow("" to FIRST_PAGE)

    val results = query
        .debounce(350)
        .filter {
            it.query.isNotEmpty()
        }.flatMapLatest {
            repository.getSearchUsers(it)
        }.asLiveData()

    fun setQuery(originalInput: String) {
        val input = originalInput.lowercase(Locale.getDefault()).trim()
        query.value = SearchRequest(input, FIRST_PAGE)
    }

    fun loadNextPage() {
        (results.value as? Resource.Success)?.run {
            if (data.incompleteResults) {
                query.value.let {
                    if (it.query.isNotBlank()) {
                        query.value = it.query to it.page + 1
                    }
                }
            }
        }
    }

    fun setIndex(index: Int) {
        _index.value = index
    }
}