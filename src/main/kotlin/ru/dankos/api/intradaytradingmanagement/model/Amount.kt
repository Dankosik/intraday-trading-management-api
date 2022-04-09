package ru.dankos.api.intradaytradingmanagement.model

data class Amount(
    val value: Long,
    val minorUnits: Int,
    val currency: String,
)
