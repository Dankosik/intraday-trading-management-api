package ru.dankos.api.intradaytradingmanagement.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Document
data class Deal(
    @Id
    val id: String? = null,
    val date: LocalDate,
    val ticker: String,
    val companyName: String,
    val purchaseAmount: Amount,
    val saleAmount: Amount,
)
