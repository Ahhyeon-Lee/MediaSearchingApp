package com.example.mediasearchingapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.coreDomain.data.SearchListData
import com.example.mediasearchingapp.ResultState
import com.example.mediasearchingapp.data.UpdateFavoriteActionType
import com.example.mediasearchingapp.mutableResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FavoriteSharedViewModel @Inject constructor() : ViewModel() {

    private val _updateFavoriteDataState = mutableResultState<Pair<UpdateFavoriteActionType, SearchListData>>()
    val updateFavoriteDataState = _updateFavoriteDataState.asStateFlow()

    fun updateFavoriteData(action: UpdateFavoriteActionType, data: SearchListData) {
        _updateFavoriteDataState.value = ResultState.Success(Pair(action, data))
    }
}