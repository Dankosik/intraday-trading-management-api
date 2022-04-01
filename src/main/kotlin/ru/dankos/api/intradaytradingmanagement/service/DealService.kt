package ru.dankos.api.intradaytradingmanagement.service

import ru.dankos.api.intradaytradingmanagement.dto.DealRequest
import ru.dankos.api.intradaytradingmanagement.model.Deal
import java.time.LocalDate

interface DealService {
    fun getDealsByDate(date: LocalDate): List<Deal>
    fun getDealById(id: String): Deal
    fun getAllDeals(): List<Deal>
    fun getDealsByTicker(ticker: String): List<Deal>
    fun createDeal(dealRequest: DealRequest): Deal
    fun updateDeal(id: String, dealRequest: DealRequest): Deal
    fun getDealsWithQueryParams(queryParams: Map<String, String>): List<Deal>
    fun getDealsByCompanyName(companyName: String): List<Deal>
}