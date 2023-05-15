package com.example.coreDomain.usecase

import com.example.commonModelUtil.data.SearchListData
import com.example.coredatabase.entity.ImageDocumentEntity
import com.example.coredatabase.entity.VideoDocumentEntity
import com.example.coredatabase.repository.FavoriteRepository
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

class GetFavoriteListFlowUseCase @Inject constructor(
    private val getFavoriteImageListFlowUseCase: GetFavoriteImageListFlowUseCase,
    private val getFavoriteVideoListFlowUseCase: GetFavoriteVideoListFlowUseCase
) {
    operator fun invoke() =
        getFavoriteImageListFlowUseCase().flatMapLatest { imgList ->
            getFavoriteVideoListFlowUseCase().map { videoList ->
                (imgList + videoList).sortedByDescending {
                    it.favoriteTime
                }
            }
        }

}