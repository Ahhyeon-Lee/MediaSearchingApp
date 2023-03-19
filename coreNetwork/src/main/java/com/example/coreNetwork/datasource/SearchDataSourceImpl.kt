package com.example.coreNetwork.datasource

import android.util.Log
import com.example.coreNetwork.service.SearchService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchDataSourceImpl @Inject constructor(
    private val service: SearchService
) : SearchDataSource {

    override fun getImageSearchResult(query: String, page: Int) {
        val result = service.getImageSearchResult(query, page)
        CoroutineScope(Dispatchers.IO).launch {
            result.collectLatest {
                Log.i("result" ,"$it")
            }
        }
    }

}