package com.alansoft.githubusersearch.ui.base

import androidx.lifecycle.*
import com.alansoft.githubusersearch.Utils.FIRST_PAGE
import com.alansoft.githubusersearch.data.Resource
import com.alansoft.githubusersearch.data.request.SearchRequest
import com.alansoft.githubusersearch.data.response.Item
import com.alansoft.githubusersearch.data.response.SearchResponse
import com.alansoft.githubusersearch.extension.to
import com.alansoft.githubusersearch.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PageViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    val selectItem = MutableLiveData<Item>()
    private val query: MutableStateFlow<SearchRequest> = MutableStateFlow("" to FIRST_PAGE)

    val remoteResults: LiveData<Resource<SearchResponse>> = query
        .debounce(350)
        .filter {
            if (it.query.isEmpty()) {
                loadLocal()
            }
            it.query.isNotEmpty()
        }.flatMapLatest {
            loadLocal(it.query)
            repository.getSearchUsers(it)
        }.asLiveData()

    private var _localResults = MutableLiveData<Resource<SearchResponse>>()
    val localResults: LiveData<Resource<SearchResponse>> get() = _localResults

    fun setQuery(originalInput: String) {
        val input = originalInput.lowercase(Locale.getDefault()).trim()
        query.value = SearchRequest(input, FIRST_PAGE)
    }

    fun loadNextPage() {
        (remoteResults.value as? Resource.Success)?.run {
            if (data.incompleteResults) {
                query.value.let {
                    if (it.query.isNotBlank()) {
                        query.value = it.query to it.page + 1
                    }
                }
            }
        }
    }

    fun loadLocal(query: String? = null) {
        viewModelScope.launch {
            repository.getLocalItems(query)
                .collectLatest {
                    _localResults.postValue(it)
                }
        }
    }

    fun insertLike(item: Item) {
        viewModelScope.launch {
            flowOf(item)
                .debounce(350)
                .filter {
                    it != null
                }.distinctUntilChanged()
                .map {
                    repository.insertItem(item)
                }.flowOn(Dispatchers.IO)
                .collectLatest {
                    if (it) {
                        selectItem.postValue(item)
                        loadLocal()
                    }
                }
        }
    }

    fun deleteItem(item: Item) {
        viewModelScope.launch {
            flowOf(item)
                .debounce(350)
                .filter {
                    it != null
                }.distinctUntilChanged()
                .map {
                    repository.deleteItem(item)
                }.flowOn(Dispatchers.IO)
                .collectLatest {
                    if (it) {
                        selectItem.postValue(item)
                        loadLocal()
                    }
                }
        }
    }
}