package com.darekbx.mineralscatalog.domain

import com.darekbx.mineralscatalog.repository.dao.MineralsDao
import com.darekbx.mineralscatalog.repository.dto.MineralDto

class AddEntryUseCase(private val mineralsDao: MineralsDao) {

    suspend operator fun invoke(uri: String, label: String, location: String, description: String) {
        val mineral = MineralDto(
            photo = uri,
            label = label,
            location = location,
            description = description,
            timestamp = System.currentTimeMillis()
        )
        mineralsDao.insertMineral(mineral)
    }
}
