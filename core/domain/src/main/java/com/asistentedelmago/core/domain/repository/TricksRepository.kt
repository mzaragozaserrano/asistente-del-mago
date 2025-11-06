package com.asistentedelmago.core.domain.repository

import com.asistentedelmago.core.model.MagicTrick
import kotlinx.coroutines.flow.Flow

interface TricksRepository {
    fun getAllTricks(userId: String): Flow<List<MagicTrick>>
    suspend fun getTrickById(id: String): MagicTrick?
    suspend fun createTrick(trick: MagicTrick)
    suspend fun updateTrick(trick: MagicTrick)
    suspend fun deleteTrick(id: String)
}

