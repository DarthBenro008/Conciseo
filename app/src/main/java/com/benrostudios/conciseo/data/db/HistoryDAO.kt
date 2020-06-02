package com.benrostudios.conciseo.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.benrostudios.conciseo.data.models.ShortnerResult


@Dao
interface HistoryDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(historyEntry: ShortnerResult)

    @Query("select * from history_table")
    fun getHistory(): LiveData<List<ShortnerResult>>

    @Delete
    fun deleteHistory(historyEntry: ShortnerResult)
}