package com.example.gympal2.core.localDB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import com.example.gympal2.feature.gym.Gym


@Dao
interface GymDao {
    @Query("SELECT * FROM $GYM_NAME")
    suspend fun getAllGyms(): List<Gym>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGyms(gyms: List<Gym>)
}


@Entity(tableName = OFFLINE_REQUEST_NAME)
data class OfflineRequestEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val requestType: String,
    val payload: String
)

@Dao
interface OfflineRequestDao {
    @Query("SELECT * FROM $OFFLINE_REQUEST_NAME")
    suspend fun getAllRequests(): List<OfflineRequestEntity>

    @Insert
    suspend fun insertRequest(request: OfflineRequestEntity)

    @Delete
    suspend fun deleteRequest(request: OfflineRequestEntity)
}