package com.example.mediasearchingapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.commonModelUtil.ResultState
import com.example.commonModelUtil.extension.onUiState
import com.example.commonModelUtil.mutableResultState
import com.example.commonModelUtil.search.ImageDocumentData
import com.example.coreDomain.usecase.GetImageSearchResultUseCase
import com.example.mediasearchingapp.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    app: Application,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val getImageSearchResultUseCase: GetImageSearchResultUseCase
) : AndroidViewModel(app) {

    private val _imageSearchListData = mutableResultState<List<ImageDocumentData>>()
    val imageSearchListData = _imageSearchListData.asStateFlow()

    fun searchImage() {
        getImageSearchResultUseCase.invoke("고양이", 1).onUiState(
            loading = {
                _imageSearchListData.value = ResultState.Loading
            },
            success = {
                _imageSearchListData.value = ResultState.Success(it.documents)
            },
            error = {
                _imageSearchListData.value = ResultState.Error(it)
            },
            finish = {
                _imageSearchListData.value = ResultState.Finish
            }
        ).launchIn(CoroutineScope(ioDispatcher))
    }
}