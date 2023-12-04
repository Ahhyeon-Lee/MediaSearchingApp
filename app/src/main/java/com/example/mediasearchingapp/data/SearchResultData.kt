package com.example.mediasearchingapp.data

import com.example.coreDomain.data.SearchListData

data class SearchResultData(
    val isNewQuerySearch: Boolean,
    val list : List<SearchListData>
)