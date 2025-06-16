package com.darekbx.mineralscatalog.domain

import com.darekbx.mineralscatalog.mappers.Mappers
import com.darekbx.mineralscatalog.model.Mineral
import com.darekbx.mineralscatalog.repository.dao.MineralsDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FetchMineralsUseCase(private val mineralsDao: MineralsDao, private val mappers: Mappers) {

    operator fun invoke(): Flow<List<Mineral>> {
        return mineralsDao.getAllMinerals().map { dtoList ->
            dtoList.map { mineralDto -> mappers.mineralDtoToDomain(mineralDto) }
        }
    }
}
