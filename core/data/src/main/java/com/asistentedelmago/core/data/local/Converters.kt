package com.asistentedelmago.core.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun fromJsonList(value: String): List<String> {
        return if (value.isEmpty() || value == "[]") {
            emptyList()
        } else {
            try {
                val listType = object : TypeToken<List<String>>() {}.type
                Gson().fromJson(value, listType)
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

    @TypeConverter
    fun toJsonList(list: List<String>): String {
        return if (list.isEmpty()) {
            "[]"
        } else {
            Gson().toJson(list)
        }
    }
}

