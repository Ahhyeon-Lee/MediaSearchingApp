package com.android.corepreference.repository

import com.android.corepreference.PreferenceManager
import com.example.coreDomain.data.SearchListData
import com.example.coreDomain.repository.FavoriteRepository
import com.google.gson.Gson
import org.json.JSONArray
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val preference: PreferenceManager
) : FavoriteRepository {

    override fun getFavoriteList(): ArrayList<SearchListData> {
        val listString = preference.get(PreferenceManager.PROPERTY_FAVORITE)
        return try {
            if (listString.isNotEmpty()) {
                val searchList = arrayListOf<SearchListData>()
                val jsonArray = JSONArray(listString)
                for (index in 0 until jsonArray.length()) {
                    if (jsonArray.getJSONObject(index).optString("type") == "image") {
                        searchList.add(
                            Gson().fromJson(
                                jsonArray.getJSONObject(index).toString(),
                                SearchListData.ImageDocumentData::class.java
                            )
                        )
                    } else {
                        searchList.add(
                            Gson().fromJson(
                                jsonArray.getJSONObject(index).toString(),
                                SearchListData.VideoDocumentData::class.java
                            )
                        )
                    }
                }
                searchList
            } else {
                ArrayList()
            }
        } catch (e: Exception) {
            ArrayList()
        }
    }

    override fun putFavoriteData(data: SearchListData) {
        val newList = getFavoriteList().apply {
            add(data)
        }
        val newListString = Gson().toJson(newList)
        preference.put(PreferenceManager.PROPERTY_FAVORITE, newListString)
    }

    override fun deleteFavoriteData(thumbnail: String) {
        val newList = getFavoriteList().apply {
            removeIf { it.thumbnail == thumbnail }
        }
        val newListString = Gson().toJson(newList)
        preference.put(PreferenceManager.PROPERTY_FAVORITE, newListString)
    }
}