package com.azielu.lorempicsumbrowser.util

import android.content.Context
import androidx.preference.PreferenceManager
import com.azielu.lorempicsumbrowser.model.ImageData
import com.google.gson.Gson

interface Cache {
    fun getFromCache(key: String): List<ImageData>?
    fun saveToCache(key: String, data: List<ImageData>)
}

class SharedPreferencesCache(context: Context) : Cache {
    private val defaultPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    override fun getFromCache(key: String): List<ImageData>? {
        val jsonSet = defaultPreferences.getStringSet(key, null)
        return jsonSet?.map {
            Gson().fromJson(it, ImageData::class.java)
        }
    }

    override fun saveToCache(key: String, data: List<ImageData>) {
        val map = data.map { Gson().toJson(it) }.toSet()
        defaultPreferences.edit().putStringSet(key, map)
            .apply()
    }
}

