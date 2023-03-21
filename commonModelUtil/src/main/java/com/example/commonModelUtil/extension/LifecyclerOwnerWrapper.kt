package com.example.commonModelUtil.extension

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.commonModelUtil.ResultState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

interface LifecycleOwnerWrapper {

    fun initLifecycleOwner(): LifecycleOwner

    fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T) -> Unit) =
        liveData.observe(this, Observer(body))

    fun <T> Flow<ResultState<T>>.launchInUiState(
        loading: suspend () -> Unit = {},
        success: suspend (T) -> Unit = {},
        error: suspend (Throwable?) -> Unit = {},
        errorWithData: suspend (Pair<Throwable?, T>) -> Unit = {},
        finish: suspend () -> Unit = {},
    ): Job = onEachState(
        loading,
        success,
        error,
        errorWithData,
        finish
    ).launchIn(initLifecycleOwner().lifecycleScope)

    fun <T> Flow<T>.onResult(action: (T) -> Unit) {
        collect(initLifecycleOwner().lifecycleScope, action)
    }

    fun <T> LiveData<T>.observe(observer: Observer<T>) {
        observe(initLifecycleOwner(), observer)
    }

    fun View.setFirstClickEvent(
        windowDuration: Long = 1000,
        onClick: () -> Unit,
    ) {
        clicks()
            .throttleFirst(windowDuration)
            .onEach { onClick.invoke() }
            .launchIn(initLifecycleOwner().lifecycleScope)
    }

}