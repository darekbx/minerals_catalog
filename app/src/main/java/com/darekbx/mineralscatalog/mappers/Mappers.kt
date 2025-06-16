package com.darekbx.mineralscatalog.mappers

import com.darekbx.mineralscatalog.model.Mineral
import com.darekbx.mineralscatalog.repository.dto.MineralDto
import java.text.SimpleDateFormat

class Mappers(private val simpleDateFormat: SimpleDateFormat) {

    fun mineralDtoToDomain(mineralDto: MineralDto): Mineral {
        return Mineral(
            id = mineralDto.id,
            dateTime = simpleDateFormat.format(mineralDto.timestamp),
            photo = mineralDto.photo,
            label = mineralDto.label,
            location = mineralDto.location,
            description = mineralDto.description
        )
    }
}
