package com.example.coreDomain.repository

import com.example.coreDomain.data.SearchListData

interface FavoriteRepository {
    fun getFavoriteList(): ArrayList<SearchListData>
    fun putFavoriteData(data: SearchListData)
    fun deleteFavoriteData(thumbnail: String)
}