package com.example.coreDomain.usecase

import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
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