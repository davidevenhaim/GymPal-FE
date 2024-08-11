package com.example.gympal2.core.localDB

import androidx.room.TypeConverter
import com.example.gympal2.feature.gym.WorkHours
import com.example.gympal2.util.Location
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Date

class Converters {
    private val gson = Gson()

    // Date converters
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    // Location converters
    @TypeConverter
    fun fromString(value: String?): Location? {
        return value?.let {
            val parts = it.split(",")
            val location = Location(Double.NaN, Double.NaN)
            location.lat = parts[0].toDouble()
            location.lng = parts[1].toDouble()
            location
        }
    }

    @TypeConverter
    fun locationToString(location: Location?): String? {
        return location?.let {
            "${it.lat},${it.lng}"
        }
    }

    //    Working Hours converters
    @TypeConverter
    fun workHoursFromString(value: String): List<WorkHours> {
        val workHours = object : TypeToken<List<WorkHours>>() {}.type

        return gson.fromJson(value, workHours)
    }

    @TypeConverter
    fun workHoursToString(value: List<WorkHours>): String {
        return gson.toJson(value)
    }
}