package ru.dankos.api.intradaytradingmanagement.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document
data class Deal(
    @Id
    val id: String? = null,
    var date: LocalDate,
    var ticker: String,
    var companyName: String,
    var purchaseAmount: Amount,
    var saleAmount: Amount
)
