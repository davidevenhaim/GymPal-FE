package com.example.gympal2.core.localDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.gympal2.feature.gym.Gym

@Database(entities = [Gym::class, OfflineRequestEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun gymDao(): GymDao
    abstract fun offlineRequestDao(): OfflineRequestDao
}

object DatabaseProvider {
    private var database: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return database ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DB_NAME
            )
                .build()
            database = instance
            instance
        }
    }
}