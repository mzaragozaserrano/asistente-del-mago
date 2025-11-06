package com.asistentedelmago.core.domain.usecase

import com.asistentedelmago.core.domain.repository.TricksRepository
import com.asistentedelmago.core.model.MagicTrick
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllTricksUseCase @Inject constructor(
    private val repository: TricksRepository
) {
    operator fun invoke(userId: String): Flow<List<MagicTrick>> {
        return repository.getAllTricks(userId)
    }
}

