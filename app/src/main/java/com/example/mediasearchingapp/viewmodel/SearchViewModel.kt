package com.example.mediasearchingapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.coreDomain.usecase.GetImageSearchResultUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    app: Application,
    private val getImageSearchResultUseCase: GetImageSearchResultUseCase
) : AndroidViewModel(app) {

    fun searchImage() {
        getImageSearchResultUseCase.invoke("고양이", 1)
    }
}