package com.darekbx.mineralscatalog.repository.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.darekbx.mineralscatalog.repository.dto.MineralDto
import kotlinx.coroutines.flow.Flow

@Dao
interface MineralsDao {

    @Insert
    suspend fun insertMineral(mineral: MineralDto)

    @Query("SELECT * FROM mineral ORDER BY timestamp DESC")
    fun getAllMinerals(): Flow<List<MineralDto>>

    @Query("SELECT * FROM mineral WHERE id = :id")
    suspend fun getMineralById(id: String): MineralDto?

    @Query("DELETE FROM mineral WHERE id = :id")
    suspend fun deleteMineralById(id: String)

    @Update
    suspend fun updateMineral(mineral: MineralDto)
}
