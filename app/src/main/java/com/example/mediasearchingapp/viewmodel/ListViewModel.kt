package com.example.mediasearchingapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.commonModelUtil.ResultState
import com.example.commonModelUtil.data.SearchListData
import com.example.commonModelUtil.mutableResultState
import com.example.coreDomain.usecase.*
import com.example.mediasearchingapp.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val getFavoriteListFlowUseCase: GetFavoriteListFlowUseCase,
    private val deleteFavoriteImageUseCase: DeleteFavoriteImageDataUseCase,
    private val deleteFavoriteVideoUseCase: DeleteFavoriteVideoDataUseCase
) : ViewModel() {

    private val _favoriteListData = mutableResultState<List<SearchListData>>()
    val favoriteListData = _favoriteListData.asStateFlow()

    fun getFavoriteList() {
        getFavoriteListFlowUseCase().onEach {
            _favoriteListData.value = ResultState.Success(it)
        }.catch {
            _favoriteListData.value = ResultState.Error(it)
        }.launchIn(CoroutineScope(ioDispatcher))
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