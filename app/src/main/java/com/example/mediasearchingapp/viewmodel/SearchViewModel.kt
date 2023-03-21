package com.example.mediasearchingapp.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.commonModelUtil.ResultState
import com.example.commonModelUtil.extension.onEachState
import com.example.commonModelUtil.extension.onState
import com.example.commonModelUtil.mutableResultState
import com.example.commonModelUtil.search.SearchData
import com.example.coreDomain.usecase.GetImageSearchResultUseCase
import com.example.coreDomain.usecase.GetVideoSearchResultUseCase
import com.example.mediasearchingapp.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    app: Application,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val getImageSearchResultUseCase: GetImageSearchResultUseCase,
    private val getVideoSearchResultUseCase: GetVideoSearchResultUseCase
) : AndroidViewModel(app) {

    private val _imageResponseData = mutableResultState<List<SearchData.ImageDocumentData>>()
    val imageResponseData = _imageResponseData.asStateFlow()
    private val _videoResponseData = mutableResultState<List<SearchData.VideoDocumentData>>()
    val videoResponseData = _videoResponseData.asStateFlow()
    private val _searchResult = mutableResultState<List<SearchData>>()
    val searchResult = _searchResult.asStateFlow()
    private val _isTyping = MutableStateFlow(false)
    val isTyping = _isTyping.asStateFlow()
    var imageSearchPage = 1
    var videoSearchPage = 1

    fun setTyping(state: Boolean) {
        _isTyping.value = state
    }

    private fun getImageResult(query: String) = flow {
        emit(getImageSearchResultUseCase.invoke2(query, imageSearchPage))
    }.catch {
        emit(emptyList())
    }.onCompletion { error ->
        if (error != null) {
            imageSearchPage++
        }
    }

    private fun getVideoResult(query: String) = flow {
        emit(getVideoSearchResultUseCase.invoke2(query, videoSearchPage))
    }.catch {
        emit(emptyList())
    }.onCompletion { error ->
        if (error != null) {
            videoSearchPage++
        }
    }

    fun search(query: String) {
        getImageResult(query).zip(getVideoResult(query)) { imageResult, videoResult ->
            mutableListOf<SearchData>().apply {
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

//    fun search(query: String) {
//        Log.i("아현", "imageSearchPage : $imageSearchPage")
//        getImageSearchResultUseCase.invoke(query, imageSearchPage).onState(CoroutineScope(ioDispatcher)) {
//            _imageResponseData.value = it
//            if (it is ResultState.Success) {
//                imageSearchPage++
//            }
//        }
//
//        Log.i("아현", "videoSearchPage : $videoSearchPage")
//        getVideoSearchResultUseCase.invoke(query, videoSearchPage).onState(CoroutineScope(ioDispatcher)) {
//            _videoResponseData.value = it
//            if (it is ResultState.Success) {
//                videoSearchPage++
//            }
//        }
//    }
}