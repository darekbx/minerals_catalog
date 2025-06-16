package com.darekbx.mineralscatalog.domain

import com.darekbx.mineralscatalog.repository.dao.MineralsDao

class DeleteEntryUseCase(private val mineralsDao: MineralsDao) {

    suspend operator fun invoke(id: String) {
        mineralsDao.deleteMineralById(id)
    }
}
