package com.example.gympal2.feature.gym

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.TextStyle
import java.util.Locale


fun checkIfGymOpen(workingHours: List<Gym.WorkHours>): Boolean {
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

fun getCurrentDay(currentTime: LocalDate): String {
    val currentDayOfWeek = currentTime.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())

    return currentDayOfWeek
}


fun changeTime(currentTime: LocalTime, hours: Int, minutes: Int): LocalTime {
    return currentTime.withHour(hours).withMinute(minutes)
}

fun extractTime(gymTime: String): Pair<Int, Int> {
    val (hours, minutes) = gymTime.split(":")

    return Pair(hours.toInt(), minutes.toInt())
}