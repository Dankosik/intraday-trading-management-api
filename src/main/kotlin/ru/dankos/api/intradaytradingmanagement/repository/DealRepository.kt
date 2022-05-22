package ru.dankos.api.intradaytradingmanagement.repository

import org.springframework.data.mongodb.repository.MongoRepository
import ru.dankos.api.intradaytradingmanagement.model.Deal
import java.time.LocalDateTime

interface DealRepository : MongoRepository<Deal, String> {
    fun findDealsByDate(date: LocalDateTime): List<Deal>
    fun findDealsByTicker(ticker: String): List<Deal>
    fun findDealsByCompanyName(companyName: String): List<Deal>
    fun findDealsByCompanyNameAndDate(companyName: String, date: LocalDateTime): List<Deal>
    fun findDealsByTickerAndDate(ticker: String, date: LocalDateTime): List<Deal>
}
