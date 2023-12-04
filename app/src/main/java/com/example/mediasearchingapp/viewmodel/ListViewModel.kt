package com.example.mediasearchingapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.coreDomain.data.SearchListData
import com.example.coreDomain.usecase.DeleteFavoriteListUseCase
import com.example.coreDomain.usecase.GetFavoriteListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val getFavoriteListUseCase: GetFavoriteListUseCase,
    private val deleteFavoriteListUseCase: DeleteFavoriteListUseCase
) : ViewModel() {

    private val _favoriteListData = MutableStateFlow<List<SearchListData>>(listOf())
    val favoriteListData = _favoriteListData.asStateFlow()

    fun getFavoriteList() {
        _favoriteListData.value = getFavoriteListUseCase.invoke().toList()
    }

    fun deleteFavoriteData(data: SearchListData) {
        deleteFavoriteListUseCase.invoke(data.thumbnail)
    }
}