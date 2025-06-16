package com.darekbx.mineralscatalog.domain

import com.darekbx.mineralscatalog.mappers.Mappers
import com.darekbx.mineralscatalog.model.Mineral
import com.darekbx.mineralscatalog.repository.dao.MineralsDao

class FetchMineralUseCase(private val mineralsDao: MineralsDao, private val mappers: Mappers) {

    suspend operator fun invoke(id: String): Mineral {
        val dto = mineralsDao.getMineralById(id)
        return mappers.mineralDtoToDomain(
            dto ?: throw IllegalArgumentException("Mineral with id $id not found")
        )
    }
}
