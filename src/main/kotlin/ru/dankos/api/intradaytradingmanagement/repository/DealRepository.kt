package ru.dankos.api.intradaytradingmanagement.repository

import org.springframework.data.mongodb.repository.MongoRepository
import ru.dankos.api.intradaytradingmanagement.model.Deal
import java.time.LocalDate

interface DealRepository : MongoRepository<Deal, String> {
    fun findDealsByDate(date: LocalDate): List<Deal>
    fun findDealsByTicker(ticker: String): List<Deal>
    fun findDealsByCompanyName(companyName: String): List<Deal>
}
