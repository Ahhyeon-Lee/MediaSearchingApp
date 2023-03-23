package com.example.mediasearchingapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.commonModelUtil.data.SearchListData
import com.example.commonModelUtil.util.PreferenceUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val preferenceUtil: PreferenceUtil,
) : ViewModel() {

    private val _favoriteListData = MutableStateFlow<List<SearchListData>>(listOf())
    val favoriteListData = _favoriteListData.asStateFlow()

    fun getFavoriteList() {
        _favoriteListData.value = preferenceUtil.getFavoriteList().toList()
    }

    fun deleteFavoriteData(data: SearchListData) {
        preferenceUtil.deleteFavoriteData(data.thumbnail)
        getFavoriteList()
    }
}