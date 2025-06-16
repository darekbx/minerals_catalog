package com.darekbx.mineralscatalog.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.darekbx.mineralscatalog.repository.dao.MineralsDao
import com.darekbx.mineralscatalog.repository.dto.MineralDto

@Database(entities = [MineralDto::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun mineralDao(): MineralsDao

    companion object {
        const val DB_NAME = "minerals_database"
    }
}
