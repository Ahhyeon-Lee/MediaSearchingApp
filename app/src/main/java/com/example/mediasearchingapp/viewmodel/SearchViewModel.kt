package com.example.mediasearchingapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.commonModelUtil.ResultState
import com.example.commonModelUtil.mutableResultState
import com.example.commonModelUtil.search.SearchListData
import com.example.coreDomain.usecase.GetImageSearchResultUseCase
import com.example.coreDomain.usecase.GetVideoSearchResultUseCase
import com.example.mediasearchingapp.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    app: Application,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val getImageSearchResultUseCase: GetImageSearchResultUseCase,
    private val getVideoSearchResultUseCase: GetVideoSearchResultUseCase
) : AndroidViewModel(app) {

    private val _searchResult = mutableResultState<List<SearchListData>>()
    val searchResult = _searchResult.asStateFlow()
    private val _isTyping = MutableStateFlow(false)
    val isTyping = _isTyping.asStateFlow()
    var currentQuery = ""
    var isImageSearchEnd = false
    var isVideoSearchEnd = false
    var imageSearchPage = 1
    var videoSearchPage = 1
    var isNewQuerySearch = false

    fun setTyping(state: Boolean) {
        _isTyping.value = state
    }

    fun resetNewQuerySearch(query: String) {
        currentQuery = query
        imageSearchPage = 1
        videoSearchPage = 1
        isNewQuerySearch = true
    }

    fun search() {
        getImageResult(currentQuery).zip(getVideoResult(currentQuery)) { imageResult, videoResult ->
            mutableListOf<SearchListData>().apply {
                addAll(imageResult)
                addAll(videoResult)
            }.sortedByDescending {
                it.getDate()
            }
        }.onEach {
            _searchResult.value = ResultState.Success(it)
        }.catch {
            _searchResult.value = ResultState.Error()
        }.launchIn(CoroutineScope(ioDispatcher))
    }

    private fun getImageResult(query: String) = flow {
        if (!isImageSearchEnd) {
            val result = getImageSearchResultUseCase.invoke(query, imageSearchPage)
            isImageSearchEnd = result.first
            emit(result.second)
        } else {
            emit(emptyList())
        }
    }.catch {
        emit(emptyList())
    }.onCompletion { error ->
        if (error == null) {
            imageSearchPage++
        }
    }

    private fun getVideoResult(query: String) = flow {
        if (!isVideoSearchEnd) {
            val result = getVideoSearchResultUseCase.invoke(query, videoSearchPage)
            isVideoSearchEnd = result.first
            emit(result.second)
        } else {
            emit(emptyList())
        }
    }.catch {
        emit(emptyList())
    }.onCompletion { error ->
        if (error == null) {
            videoSearchPage++
        }
    }
}