package com.example.mediasearchingapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coreDomain.data.SearchListData
import com.example.coreDomain.usecase.DeleteFavoriteListUseCase
import com.example.coreDomain.usecase.GetImageSearchResultUseCase
import com.example.coreDomain.usecase.GetVideoSearchResultUseCase
import com.example.coreDomain.usecase.PutFavoriteListUseCase
import com.example.mediasearchingapp.ResultState
import com.example.mediasearchingapp.data.SearchResultData
import com.example.mediasearchingapp.data.UpdateFavoriteActionType
import com.example.mediasearchingapp.di.DefaultDispatcher
import com.example.mediasearchingapp.extension.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val getImageSearchResultUseCase: GetImageSearchResultUseCase,
    private val getVideoSearchResultUseCase: GetVideoSearchResultUseCase,
    private val putFavoriteListUseCase: PutFavoriteListUseCase,
    private val deleteFavoriteListUseCase: DeleteFavoriteListUseCase
) : ViewModel() {

    private val _searchResult = MutableLiveData<ResultState<SearchResultData>>()
    val searchResult = _searchResult.asLiveData()
    private val _isTyping = MutableStateFlow(false)
    val isTyping = _isTyping.asStateFlow()
    private val _showBtnUp = MutableStateFlow<Boolean?>(null)
    val showBtnUp = _showBtnUp.asStateFlow()
    private val totalSearchList = mutableListOf<SearchListData>()
    private var currentQuery = ""
    private var isImageSearchEnd = false
    private var isVideoSearchEnd = false
    private var imageSearchPage = 1
    private var videoSearchPage = 1
    private var totalPage = 1
    private var isNewQuerySearch = false
    private var searchingJob: Job? = null

    fun setTyping(state: Boolean) {
        _isTyping.value = state
    }

    fun showBtnUp(state: Boolean) {
        _showBtnUp.value = state
    }

    fun resetNewQuerySearch(query: String? = null) {
        query?.let { currentQuery = it }
        imageSearchPage = 1
        videoSearchPage = 1
        totalPage = 1
        totalSearchList.clear()
        isNewQuerySearch = true
        isImageSearchEnd = false
        isVideoSearchEnd = false
    }

    fun search() {
        if (searchingJob?.isActive == true) searchingJob?.cancel()
        searchingJob = getImageResult(currentQuery).zip(getVideoResult(currentQuery)) { imageResult, videoResult ->
            if (imageResult is ResultState.Error && videoResult is ResultState.Error) {
                imageResult.error?.let { throw it }
            }

            val imageList = if (imageResult is ResultState.Success) imageResult.data else emptyList()
            val videoList = if (videoResult is ResultState.Success) videoResult.data else emptyList()
            val list = (imageList + videoList).sortedByDescending { it.getDate() }
                .takeIf { it.isNotEmpty() }
                ?.let {
                    (listOf(SearchListData.PageData(totalPage++)) + it)
                } ?: emptyList()
            totalSearchList.addAll(list)

            SearchResultData(isNewQuerySearch, totalSearchList)
        }.flowOn(defaultDispatcher).onEach {
            _searchResult.value = ResultState.Success(it)
            isNewQuerySearch = false
        }.catch {
            _searchResult.value = ResultState.Error(it)
        }.launchIn(viewModelScope)
    }

    private fun getImageResult(query: String) =
        flow<ResultState<List<SearchListData.ImageDocumentData>>> {
            if (isImageSearchEnd) {
                emit(ResultState.Success(emptyList()))
            } else {
                val result = getImageSearchResultUseCase.invoke(query, imageSearchPage)
                isImageSearchEnd = result.isEnd
                emit(ResultState.Success(result.list))
            }
        }.catch {
            emit(ResultState.Error(it))
        }.onCompletion { error ->
            if (error == null) {
                imageSearchPage++
            }
        }

    private fun getVideoResult(query: String) =
        flow<ResultState<List<SearchListData.VideoDocumentData>>> {
            if (isVideoSearchEnd) {
                emit(ResultState.Success(emptyList()))
            } else {
                val result = getVideoSearchResultUseCase.invoke(query, videoSearchPage)
                isVideoSearchEnd = result.isEnd
                emit(ResultState.Success(result.list))
            }
        }.catch {
            emit(ResultState.Error(it))
        }.onCompletion { error ->
            if (error == null) {
                videoSearchPage++
            }
        }

    fun editFavoriteItem(action: UpdateFavoriteActionType, data: SearchListData) {
        val index = totalSearchList.indexOf(data)
        if (index < 0) return

        val isFavorite = action == UpdateFavoriteActionType.ADD
        val listData = when (data) {
            is SearchListData.ImageDocumentData -> data.copy(isFavorite = isFavorite)
            is SearchListData.VideoDocumentData -> data.copy(isFavorite = isFavorite)
            else -> data
        }

        if (isFavorite) putFavoriteListUseCase.invoke(listData)
        else deleteFavoriteListUseCase.invoke(listData.thumbnail)

        totalSearchList.removeAt(index)
        totalSearchList.add(index, listData)
        _searchResult.value = ResultState.Success(SearchResultData(false, totalSearchList))
    }
}