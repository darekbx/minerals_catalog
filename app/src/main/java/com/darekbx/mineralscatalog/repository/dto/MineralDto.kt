package com.darekbx.mineralscatalog.repository.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "mineral")
data class MineralDto(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val timestamp: Long,
    val photo: String,
    val label: String,
    val location: String,
    val description: String,
)
