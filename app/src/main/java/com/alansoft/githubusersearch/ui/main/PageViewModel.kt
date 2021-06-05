package com.alansoft.githubusersearch.ui.main

import androidx.lifecycle.*
import com.alansoft.githubusersearch.Utils.FIRST_PAGE
import com.alansoft.githubusersearch.data.Resource
import com.alansoft.githubusersearch.data.request.SearchRequest
import com.alansoft.githubusersearch.data.response.SearchResponse
import com.alansoft.githubusersearch.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PageViewModel @Inject constructor(
    repository: MainRepository
) : ViewModel() {

    private val _index = MutableLiveData<Int>()
    val text: LiveData<String> = Transformations.map(_index) {
        "Hello world from section: $it"
    }

    private val query = MutableStateFlow(SearchRequest("", FIRST_PAGE))

    val results: LiveData<Resource<SearchResponse>> = query
        .filter {
            it.query.isNotEmpty()
        }.flatMapLatest {
            repository.getSearchUsers(it)
        }.asLiveData()

    fun setQuery(originalInput: String) {
        val input = originalInput.lowercase(Locale.getDefault()).trim()
        query.value = SearchRequest(originalInput, FIRST_PAGE)
    }

    fun loadNextPage() {
        (results.value as? Resource.Success)?.run {
            if (!data.incompleteResults) {
                query.value.let {
//                    if (it.first.isNotBlank()) {
//                        query.value = it.first to data.meta.page + 1
//                    }
                }
            }
        }
    }

    fun setIndex(index: Int) {
        _index.value = index
    }
}