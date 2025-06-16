package com.darekbx.mineralscatalog.model

data class Mineral(
    val id: String,
    val dateTime: String,
    val photo: String,
    val label: String,
    val location: String,
    val description: String,
)
