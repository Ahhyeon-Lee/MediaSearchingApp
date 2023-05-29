package com.example.mediasearchingapp.viewmodel

import android.content.res.Configuration
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coreDomain.data.SearchListData
import com.example.coreDomain.usecase.*
import com.example.mediasearchingapp.di.DefaultDispatcher
import com.example.mediasearchingapp.di.IoDispatcher
import com.example.mediasearchingapp.extension.ResultState
import com.example.mediasearchingapp.extension.mutableResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val getImageSearchResultUseCase: GetImageSearchResultUseCase,
    private val getVideoSearchResultUseCase: GetVideoSearchResultUseCase,
    private val getFavoriteImageListOnceUseCase: GetFavoriteImageListOnceUseCase,
    private val getFavoriteVideoListOnceUseCase: GetFavoriteVideoListOnceUseCase,
    private val putFavoriteImageUseCase: PutFavoriteImageDataUseCase,
    private val putFavoriteVideoUseCase: PutFavoriteVideoDataUseCase,
    private val deleteFavoriteImageUseCase: DeleteFavoriteImageDataUseCase,
    private val deleteFavoriteVideoUseCase: DeleteFavoriteVideoDataUseCase
) : ViewModel() {

    private val _searchResult = mutableResultState<List<SearchListData>>()
    val searchResult = _searchResult.asStateFlow()
    private val _isResultEmpty = MutableLiveData<Boolean?>(null)
    val isResultEmpty = _isResultEmpty as LiveData<Boolean?>
    private val _favoriteResult = MutableLiveData<List<SearchListData>>(listOf())
    val favoriteResult = _favoriteResult as LiveData<List<SearchListData>>
    private val _isTyping = MutableStateFlow(false)
    val isTyping = _isTyping.asStateFlow()
    private val _showBtnUp = MutableStateFlow<Boolean?>(null)
    val showBtnUp = _showBtnUp.asStateFlow()
    var prevOrientation = Configuration.ORIENTATION_PORTRAIT
    var currentQuery = ""
    var isImageSearchEnd = false
    var isVideoSearchEnd = false
    var imageSearchPage = 1
    var videoSearchPage = 1
    var totalPage = 1
    var isNewQuerySearch = false
    var isCallFinished = true
    var searchingJob: Job? = null

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
        isImageSearchEnd = false
    }

    fun search() {
        if (searchingJob?.isActive == true) searchingJob?.cancel()
        searchingJob = CoroutineScope(defaultDispatcher).launch {
            getImageResult(currentQuery).zip(getVideoResult(currentQuery)) { imageResult, videoResult ->
                (imageResult + videoResult).sortedByDescending {
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
                if (it.isEmpty()) _isResultEmpty.postValue(true)
            }.catch {
                _searchResult.value = ResultState.Error(it)
            }.onCompletion {
                isCallFinished = true
            }.launchIn(this)
        }
    }

    fun getFavoriteList() = CoroutineScope(ioDispatcher).launch {
        val favImageList = async { getFavoriteImageListOnceUseCase() }
        val favVideoList = async { getFavoriteVideoListOnceUseCase() }
        _favoriteResult.postValue(favImageList.await() + favVideoList.await())
    }

    private fun getImageResult(query: String) = flow {
        if (!isImageSearchEnd) {
            val result = getImageSearchResultUseCase.invoke(
                query,
                imageSearchPage,
                getFavoriteImageListOnceUseCase()
            )
            isImageSearchEnd = result.isEnd
            emit(result.list)
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
            val result = getVideoSearchResultUseCase.invoke(
                query,
                videoSearchPage,
                getFavoriteVideoListOnceUseCase()
            )
            isVideoSearchEnd = result.isEnd
            emit(result.list)
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

    fun putFavoriteData(data: SearchListData) = CoroutineScope(ioDispatcher).launch {
        when (data) {
            is SearchListData.ImageDocumentData -> {
                putFavoriteImageUseCase.invoke(data)
            }
            is SearchListData.VideoDocumentData -> {
                putFavoriteVideoUseCase.invoke(data)
            }
            else -> {}
        }
    }

    fun deleteFavoriteData(data: SearchListData) = CoroutineScope(ioDispatcher).launch {
        when (data) {
            is SearchListData.ImageDocumentData -> {
                deleteFavoriteImageUseCase.invoke(data)
            }
            is SearchListData.VideoDocumentData -> {
                deleteFavoriteVideoUseCase.invoke(data)
            }
            else -> {}
        }
    }
}