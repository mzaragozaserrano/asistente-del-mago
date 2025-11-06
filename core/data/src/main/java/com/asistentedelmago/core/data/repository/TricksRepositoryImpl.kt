package com.asistentedelmago.core.data.repository

import com.asistentedelmago.core.data.local.TrickDao
import com.asistentedelmago.core.data.local.TrickEntity
import com.asistentedelmago.core.domain.repository.TricksRepository
import com.asistentedelmago.core.model.MagicTrick
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TricksRepositoryImpl(
    private val trickDao: TrickDao
) : TricksRepository {
    
    override fun getAllTricks(userId: String): Flow<List<MagicTrick>> {
        return trickDao.getAllTricks(userId).map { entities ->
            entities.map { it.toMagicTrick() }
        }
    }
    
    override suspend fun getTrickById(id: String): MagicTrick? {
        return trickDao.getTrickById(id)?.toMagicTrick()
    }
    
    override suspend fun createTrick(trick: MagicTrick) {
        trickDao.insertTrick(TrickEntity.fromMagicTrick(trick))
    }
    
    override suspend fun updateTrick(trick: MagicTrick) {
        trickDao.updateTrick(TrickEntity.fromMagicTrick(trick))
    }
    
    override suspend fun deleteTrick(id: String) {
        trickDao.deleteTrick(id)
    }
}

