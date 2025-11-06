package com.asistentedelmago.core.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Converters {
    private val gson: Gson = Gson()

    @JvmStatic
    @TypeConverter
    fun fromJsonList(value: String): List<String> {
        return if (value.isEmpty() || value == "[]") {
            emptyList()
        } else {
            try {
                val listType = object : TypeToken<List<String>>() {}.type
                gson.fromJson<List<String>>(value, listType)
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    @JvmStatic
    @TypeConverter
    fun toJsonList(list: List<String>): String {
        return if (list.isEmpty()) {
            "[]"
        } else {
            gson.toJson(list)
        }
    }
}

