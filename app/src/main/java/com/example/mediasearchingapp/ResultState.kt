package com.example.mediasearchingapp

import kotlinx.coroutines.flow.MutableStateFlow

fun <T> mutableResultState(
    uiState: ResultState<T> = ResultState.UnInitialize
): MutableStateFlow<ResultState<T>> = MutableStateFlow(uiState)

sealed interface ResultState<out T> {
    object UnInitialize : ResultState<Nothing>
    data class Success<T>(val data: T) : ResultState<T>
    data class Error(val error: Throwable? = null) : ResultState<Nothing>
    data class ErrorWithData<T>(val error: Throwable? = null, val data: T) : ResultState<T>
    object Loading : ResultState<Nothing>
    object Finish : ResultState<Nothing>
}