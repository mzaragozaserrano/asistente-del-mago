package com.asistentedelmago.core.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.asistentedelmago.core.model.MagicTrick

@Entity(tableName = "tricks")
data class TrickEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val title: String,
    val description: String?,
    val materials: String?,
    val angles: String?,
    val resetTime: String?,
    val videoLinks: String, // JSON string
    val tags: String, // JSON string
    val createdAt: Long,
    val lastSynced: Long = System.currentTimeMillis()
) {
    fun toMagicTrick(): MagicTrick {
        return MagicTrick(
            id = id,
            userId = userId,
            title = title,
            description = description,
            materials = materials,
            angles = angles,
            resetTime = resetTime,
            videoLinks = Converters.fromJsonList(videoLinks),
            tags = Converters.fromJsonList(tags),
            createdAt = createdAt
        )
    }
    
    companion object {
        fun fromMagicTrick(trick: MagicTrick): TrickEntity {
            return TrickEntity(
                id = trick.id,
                userId = trick.userId,
                title = trick.title,
                description = trick.description,
                materials = trick.materials,
                angles = trick.angles,
                resetTime = trick.resetTime,
                videoLinks = Converters.toJsonList(trick.videoLinks),
                tags = Converters.toJsonList(trick.tags),
                createdAt = trick.createdAt
            )
        }
    }
}

