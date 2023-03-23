package com.example.commonModelUtil.util

import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.commonModelUtil.data.SearchListData
import com.google.gson.Gson
import org.json.JSONArray
import javax.inject.Inject

class PreferenceUtil @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    companion object {
        private const val PROPERTY_FAVORITE = "PROPERTY_FAVORITE"
    }

    fun getFavoriteList(): ArrayList<SearchListData> {
        val listString = get(PROPERTY_FAVORITE)
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

    fun putFavoriteData(data: SearchListData) {
        val newList = getFavoriteList().apply {
            add(data)
        }
        val newListString = Gson().toJson(newList)
        put(PROPERTY_FAVORITE, newListString)
    }

    fun deleteFavoriteData(thumbnail: String) {
        val newList = getFavoriteList().apply {
            removeIf { it.thumbnail == thumbnail }
        }
        val newListString = Gson().toJson(newList)
        put(PROPERTY_FAVORITE, newListString)
    }

    private fun put(key: String, value: String) =
        sharedPreferences.edit {
            putString(key, value)
        }

    private fun get(key: String, def: String = ""): String =
        sharedPreferences.getString(key, def) ?: def
}