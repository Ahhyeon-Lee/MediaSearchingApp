package com.example.coreDomain.usecase

import com.example.coreDomain.data.SearchListData
import javax.inject.Inject

class GerFavoriteImageListUseCase @Inject constructor(
    private val getFavoriteListUseCase: GetFavoriteListUseCase
) {

    fun invoke() =
        getFavoriteListUseCase.invoke().filterIsInstance<SearchListData.ImageDocumentData>()
}