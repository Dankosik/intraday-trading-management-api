package ru.dankos.api.intradaytradingmanagement.service.impl

import org.springframework.stereotype.Service
import ru.dankos.api.intradaytradingmanagement.model.Deal
import ru.dankos.api.intradaytradingmanagement.repository.DealRepository
import ru.dankos.api.intradaytradingmanagement.service.DealService
import ru.dankos.api.intradaytradingmanagement.service.exception.ServiceException
import java.time.LocalDate

@Service
class DealServiceImpl(
    val dealRepository: DealRepository
) : DealService {

    override fun getDealsByDate(date: LocalDate): List<Deal> {
        return dealRepository.findDealsByDate(date)
    }

    override fun getDealById(id: String): Deal {
        return dealRepository.findById(id).orElseThrow { ServiceException("The Deal is not found") }
    }

    override fun getAllDeals(): List<Deal> {
        return dealRepository.findAll()
    }

    override fun getDealsByTicker(ticker: String): List<Deal> {
        return dealRepository.findDealsByTicker(ticker)
    }

    override fun getDealsByCompanyName(companyName: String): List<Deal> {
        return dealRepository.findDealsByCompanyName(companyName)
    }

    override fun createDeal(deal: Deal): Deal {
        return dealRepository.save(deal)
    }

    override fun getDealsByCustomQuery(customQuery: Map<String, String>): List<Deal> {
        if (customQuery.size > 1) throw ServiceException("Supported number of query parameters: 1")
        customQuery.entries.forEach {
            //todo move strings to enum
            when (it.key) {
                "companyName" -> return getDealsByCompanyName(it.value)
                "ticker" -> return getDealsByTicker(it.value)
                "date" -> return getDealsByDate(LocalDate.parse(it.value))
            }
        }
        throw ServiceException("Not supported query parameter")
    }
}