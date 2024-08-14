package com.example.gympal2.feature.gym


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.gympal2.core.localDB.GYM_NAME
import com.example.gympal2.util.Location
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.TextStyle
import java.util.Date
import java.util.Locale

@Entity(tableName = GYM_NAME)
data class Gym(
    @PrimaryKey val id: String,
    val createdAt: Date,
    val location: Location,
    val name: String,
    val rating: Int,
    val workingHours: List<WorkHours>,
) {
    val isOpen: Boolean
        get() = checkIfGymOpen(workingHours)

    private fun checkIfGymOpen(workingHours: List<WorkHours>): Boolean {

        fun getCurrentDay(currentTime: LocalDate): String =
            currentTime.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())

        fun changeTime(currentTime: LocalTime, hours: Int, minutes: Int): LocalTime =
            currentTime.withHour(hours).withMinute(minutes)

        fun extractTime(gymTime: String): Pair<Int, Int> {
            val (hours, minutes) = gymTime.split(":")
            return Pair(hours.toInt(), minutes.toInt())
        }

        val currentDate = LocalDate.now()
        val currentTime = LocalTime.now()
        val currentDay = getCurrentDay(currentDate)
        val currentWorkingHours = workingHours.find { currentDay == it.day.name }

        if (currentWorkingHours == null) {
            return false
        }

        val openHourMinute = extractTime(currentWorkingHours.start)
        val openTime = changeTime(LocalTime.now(), openHourMinute.first, openHourMinute.second)

        val closeHourMinute = extractTime(currentWorkingHours.end)
        val closeTime = changeTime(LocalTime.now(), closeHourMinute.first, closeHourMinute.second)


        return currentTime > openTime && currentTime < closeTime
    }

    data class WorkHours(
        val isOpen: Boolean,
        val start: String,
        val end: String,
        val day: DaysOfWeek,
    ) {
        enum class DaysOfWeek {
            // TODO: see why it's never used
            Sunday,
            Monday,
            Tuesday,
            Wednesday,
            Thursday,
            Friday,
            Saturday,
        }
    }
}

const val GYM_POLLING_DELAY = 20000
//that's the only constant in here...