package com.example.mediasearchingapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.coreDomain.data.SearchListData
import com.example.coreDomain.usecase.DeleteFavoriteImageDataUseCase
import com.example.coreDomain.usecase.DeleteFavoriteVideoDataUseCase
import com.example.coreDomain.usecase.GetFavoriteListFlowUseCase
import com.example.mediasearchingapp.di.IoDispatcher
import com.example.mediasearchingapp.extension.ResultState
import com.example.mediasearchingapp.extension.mutableResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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