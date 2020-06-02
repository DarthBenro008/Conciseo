package com.benrostudios.conciseo.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.benrostudios.conciseo.data.models.ShortnerResult


@Database(
    entities = [ShortnerResult::class],
    version = 1
)
abstract class HistoryDatabase : RoomDatabase() {
    abstract fun historyDAO(): HistoryDAO
    companion object {
        @Volatile
        private var instance: HistoryDatabase? = null
        private var LOCK = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }
        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            HistoryDatabase::class.java,
            "history.db"
        ).build()
    }

}