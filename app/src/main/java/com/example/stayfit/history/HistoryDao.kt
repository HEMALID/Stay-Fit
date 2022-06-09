package com.example.stayfit.history

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface HistoryDao {

    @Insert
    suspend fun insert(historyDao: HistoryEntity)

    @Query("SELECT * FROM `history-table` ")
    fun fetchAllDates(): Flow<List<HistoryEntity>>

}