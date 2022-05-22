package ru.dankos.api.intradaytradingmanagement.dto

import ru.dankos.api.intradaytradingmanagement.model.Amount
import java.time.LocalDate

class DealResponse(
    val id: String,
    var date: LocalDate,
    var ticker: String,
    var companyName: String,
    var purchaseAmount: Amount,
    var saleAmount: Amount
)
