package com.example.coreDomain.usecase

import com.example.coreDomain.data.SearchListData
import javax.inject.Inject

class GerFavoriteVideoListUseCase @Inject constructor(
    private val getFavoriteListUseCase: GetFavoriteListUseCase
) {

    fun invoke() =
        getFavoriteListUseCase.invoke().filterIsInstance<SearchListData.VideoDocumentData>()
}