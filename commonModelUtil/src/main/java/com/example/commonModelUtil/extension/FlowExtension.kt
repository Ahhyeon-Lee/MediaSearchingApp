package com.example.commonModelUtil.extension

import com.example.commonModelUtil.ResultState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*

/** Flow 내 데이터를 ResultState로 묶어 매핑 및 emit */
fun <T> Flow<T>.resultState(): Flow<ResultState<T>> {
    return this
        .map<T, ResultState<T>> {
            ResultState.Success(it)
        }
        .onStart { emit(ResultState.Loading) }
        .catch { emit(ResultState.Error(it)) }
        .onCompletion { emit(ResultState.Finish) }
}

/** 로딩, 성공, 에러, 에러 with data, 완료시 각각 다른 처리를 해야할 때 사용 */
fun <T> Flow<ResultState<T>>.onEachState(
    loading: suspend () -> Unit = {},
    success: suspend (T) -> Unit = {},
    error: suspend (Throwable?) -> Unit = {},
    errorWithData: suspend (Pair<Throwable?, T>) -> Unit = {},
    finish: suspend () -> Unit = {}
): Flow<ResultState<T>> = onEach { state ->
    when (state) {
        is ResultState.Loading -> {
            loading.invoke()
        }
        is ResultState.Success -> {
            success.invoke(state.data)
        }
        is ResultState.Error -> {
            error.invoke(state.error)
        }
        is ResultState.ErrorWithData -> {
            errorWithData.invoke(Pair(state.error, state.data))
        }
        else -> {
            finish.invoke()
        }
    }
}

inline fun <T> Flow<T>.collect(
    externalScope: CoroutineScope,
    crossinline collect: (T) -> Unit
) = onEach { collect.invoke(it) }.launchIn(externalScope)