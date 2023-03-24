package com.example.mediasearchingapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.commonModelUtil.ResultState
import com.example.commonModelUtil.mutableResultState
import com.example.commonModelUtil.data.SearchListData
import com.example.commonModelUtil.util.PreferenceUtil
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
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val preferenceUtil: PreferenceUtil,
    private val getImageSearchResultUseCase: GetImageSearchResultUseCase,
    private val getVideoSearchResultUseCase: GetVideoSearchResultUseCase,
) : ViewModel() {

    private val _searchResult = mutableResultState<List<SearchListData>>()
    val searchResult = _searchResult.asStateFlow()
    private val _isTyping = MutableStateFlow(false)
    val isTyping = _isTyping.asStateFlow()
    private val _showBtnUp = MutableStateFlow<Boolean?>(null)
    val showBtnUp = _showBtnUp.asStateFlow()
    var currentQuery = ""
    var isImageSearchEnd = false
    var isVideoSearchEnd = false
    var imageSearchPage = 1
    var videoSearchPage = 1
    var totalPage = 1
    var isNewQuerySearch = false
    var isCallFinished = true

    fun setTyping(state: Boolean) {
        _isTyping.value = state
    }

    fun showBtnUp(state: Boolean) {
        _showBtnUp.value = state
    }

    fun resetNewQuerySearch(query: String) {
        currentQuery = query
        imageSearchPage = 1
        videoSearchPage = 1
        totalPage = 1
        isNewQuerySearch = true
    }

    fun search() {
        if (!isCallFinished) return
        getImageResult(currentQuery).zip(getVideoResult(currentQuery)) { imageResult, videoResult ->
            mutableListOf<SearchListData>().apply {
                addAll(imageResult)
                addAll(videoResult)
            }.sortedByDescending {
                it.getDate()
            }.toMutableList().apply {
                if (isNotEmpty()) {
                    add(0, SearchListData.PageData(totalPage++))
                }
            }
        }.onStart {
            isCallFinished = false
        }.onEach {
            _searchResult.value = ResultState.Success(it)
        }.catch {
            _searchResult.value = ResultState.Error(it)
        }.onCompletion {
            isCallFinished = true
        }.launchIn(CoroutineScope(ioDispatcher))
    }

    fun getFavoriteList() = preferenceUtil.getFavoriteList().toList()

    private fun getFavoriteImageList() =
        preferenceUtil.getFavoriteList().filterIsInstance<SearchListData.ImageDocumentData>()

    private fun getFavoriteVideoList() =
        preferenceUtil.getFavoriteList().filterIsInstance<SearchListData.VideoDocumentData>()

    private fun getImageResult(query: String) = flow {
        if (!isImageSearchEnd) {
            val result =
                getImageSearchResultUseCase.invoke(query, imageSearchPage, getFavoriteImageList())
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
            val result =
                getVideoSearchResultUseCase.invoke(query, videoSearchPage, getFavoriteVideoList())
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