package com.asistentedelmago.core.domain.usecase

import com.asistentedelmago.core.domain.repository.TricksRepository
import com.asistentedelmago.core.model.MagicTrick
import java.util.UUID

class CreateTrickUseCase(
    private val repository: TricksRepository
) {
    suspend operator fun invoke(
        userId: String,
        title: String,
        description: String? = null,
        materials: String? = null,
        angles: String? = null,
        resetTime: String? = null
    ) {
        val trick = MagicTrick(
            id = UUID.randomUUID().toString(),
            userId = userId,
            title = title,
            description = description,
            materials = materials,
            angles = angles,
            resetTime = resetTime,
            videoLinks = emptyList(),
            tags = emptyList(),
            createdAt = System.currentTimeMillis()
        )
        repository.createTrick(trick)
    }
}

