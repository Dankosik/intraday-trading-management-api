package ru.dankos.api.intradaytradingmanagement.dto

import ru.dankos.api.intradaytradingmanagement.model.Amount
import java.time.LocalDate

data class DealRequest(
    val date: LocalDate,
    val ticker: String,
    val companyName: String,
    val purchaseAmount: Amount,
    val saleAmount: Amount
)