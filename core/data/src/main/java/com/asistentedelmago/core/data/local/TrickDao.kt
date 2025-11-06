package com.asistentedelmago.core.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TrickDao {
    @Query("SELECT * FROM tricks WHERE userId = :userId ORDER BY createdAt DESC")
    fun getAllTricks(userId: String): Flow<List<TrickEntity>>
    
    @Query("SELECT * FROM tricks WHERE id = :id")
    suspend fun getTrickById(id: String): TrickEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrick(trick: TrickEntity)
    
    @Update
    suspend fun updateTrick(trick: TrickEntity)
    
    @Query("DELETE FROM tricks WHERE id = :id")
    suspend fun deleteTrick(id: String)
}

