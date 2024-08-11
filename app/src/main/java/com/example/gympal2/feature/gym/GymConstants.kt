package com.example.gympal2.feature.gym


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.gympal2.core.localDB.GYM_NAME
import com.example.gympal2.util.Location
import java.util.Date

@Entity(tableName = GYM_NAME)
data class Gym(
    @PrimaryKey val id: String,
    val createdAt: Date,
    val location: Location,
    val name: String,
    val rating: Int,
    val workingHours: List<WorkHours>,
)


data class WorkHours(
    val isOpen: Boolean,
    val start: String,
    val end: String,
    val day: DaysOfWeek,

    )

enum class DaysOfWeek {
    Sunday,
    Monday,
    Tuesday,
    Wednesday,
    Thursday,
    Friday,
    Saturday,
}

const val GYM_POLLING_DELAY = 20000