package ru.dankos.api.intradaytradingmanagement.service

import org.springframework.stereotype.Service
import ru.dankos.api.intradaytradingmanagement.dto.DealRequest
import ru.dankos.api.intradaytradingmanagement.dto.DealResponse
import ru.dankos.api.intradaytradingmanagement.dto.DealsRequest
import ru.dankos.api.intradaytradingmanagement.dto.DealsRequestType
import ru.dankos.api.intradaytradingmanagement.dto.DealsRequestType.GET_ALL
import ru.dankos.api.intradaytradingmanagement.dto.DealsRequestType.GET_BY_COMPANY_NAME
import ru.dankos.api.intradaytradingmanagement.dto.DealsRequestType.GET_BY_COMPANY_NAME_AND_DATE
import ru.dankos.api.intradaytradingmanagement.dto.DealsRequestType.GET_BY_DATE
import ru.dankos.api.intradaytradingmanagement.dto.DealsRequestType.GET_BY_TICKER
import ru.dankos.api.intradaytradingmanagement.dto.DealsRequestType.GET_BY_TICKER_AND_DATE
import ru.dankos.api.intradaytradingmanagement.model.Deal
import ru.dankos.api.intradaytradingmanagement.repository.DealRepository
import ru.dankos.api.intradaytradingmanagement.service.exception.EntityNotFoundException
import ru.dankos.api.intradaytradingmanagement.service.exception.ServiceException
import java.time.LocalDate

@Service
class DealService(
    private val dealRepository: DealRepository
) {

    fun createDeal(dealRequest: DealRequest): DealResponse =
        dealRepository.save(dealRequest.toEntity()).toDealResponse()

    fun getDealById(id: String): DealResponse = dealRepository.findById(id).map { it.toDealResponse() }
        .orElseThrow { EntityNotFoundException("The Deal with id: $id is not found") }

    fun getDeals(dealsRequest: DealsRequest): List<DealResponse> {
        val typeOfRequest = getTypeOfDealsRequest(dealsRequest)
        if (typeOfRequest == GET_ALL) {
            return getAllDeals()
        }
        if (typeOfRequest == GET_BY_TICKER_AND_DATE) {
            return dealsRequest.ticker?.let {
                getDealsByTickerAndDate(it, LocalDate.parse(dealsRequest.date))
            } ?: emptyList()
        }
        if (typeOfRequest == GET_BY_TICKER) {
            return dealsRequest.ticker?.let { getDealsByTicker(it) } ?: emptyList()
        }
        if (typeOfRequest == GET_BY_COMPANY_NAME) {
            return dealsRequest.companyName?.let { getDealsByCompanyName(it) } ?: emptyList()
        }
        if (typeOfRequest == GET_BY_DATE) {
            return getDealsByDate(LocalDate.parse(dealsRequest.date))
        }
        if (typeOfRequest == GET_BY_COMPANY_NAME_AND_DATE) {
            return dealsRequest.companyName?.let {
                getDealsByCompanyNameAndDate(it, LocalDate.parse(dealsRequest.date))
            } ?: emptyList()
        }

        throw ServiceException("Request is not correct")
    }

    fun updateDeal(id: String, dealRequest: DealRequest): DealResponse {
        val foundDeal = dealRepository.findById(id)
            .orElseThrow { EntityNotFoundException("The Deal with id: $id is not found") }
        return dealRepository.save(
            foundDeal.apply {
                date = dealRequest.date
                ticker = dealRequest.ticker
                companyName = dealRequest.companyName
                purchaseAmount = dealRequest.purchaseAmount
                saleAmount = dealRequest.saleAmount
            }
        ).toDealResponse()
    }


    private fun getAllDeals(): List<DealResponse> = dealRepository.findAll().map { it.toDealResponse() }

    private fun getDealsByDate(date: LocalDate): List<DealResponse> =
        dealRepository.findDealsByDate(date.atStartOfDay()).map { it.toDealResponse() }

    private fun getDealsByTickerAndDate(ticker: String, date: LocalDate): List<DealResponse> =
        dealRepository.findDealsByTickerAndDate(
            ticker,
            date.atStartOfDay()
        ).map { it.toDealResponse() }

    private fun getDealsByCompanyNameAndDate(companyName: String, date: LocalDate) =
        dealRepository.findDealsByCompanyNameAndDate(
            companyName,
            date.atStartOfDay()
        ).map { it.toDealResponse() }

    private fun getDealsByTicker(ticker: String): List<DealResponse> =
        dealRepository.findDealsByTicker(ticker).map { it.toDealResponse() }

    private fun getDealsByCompanyName(companyName: String): List<DealResponse> =
        dealRepository.findDealsByCompanyName(companyName).map { it.toDealResponse() }

    private fun getTypeOfDealsRequest(dealsRequest: DealsRequest): DealsRequestType {
        if ((dealsRequest.date != null && dealsRequest.ticker != null) && dealsRequest.companyName == null) {
            return GET_BY_TICKER_AND_DATE
        }
        if ((dealsRequest.date != null && dealsRequest.companyName != null) && dealsRequest.ticker == null) {
            return GET_BY_COMPANY_NAME_AND_DATE
        }
        if (dealsRequest.companyName != null && (dealsRequest.date == null && dealsRequest.ticker == null)) {
            return GET_BY_COMPANY_NAME
        }
        if ((dealsRequest.date == null && dealsRequest.companyName == null) && dealsRequest.ticker != null) {
            return GET_BY_TICKER
        }
        if (dealsRequest.date != null && dealsRequest.companyName == null) {
            return GET_BY_DATE
        }

        return GET_ALL
    }

    private fun DealRequest.toEntity(): Deal = Deal(
        date = this.date,
        ticker = this.ticker,
        companyName = this.companyName,
        purchaseAmount = this.purchaseAmount,
        saleAmount = this.saleAmount,
    )

    private fun Deal.toDealResponse(): DealResponse = DealResponse(
        id = this.id ?: "",
        date = this.date,
        ticker = this.ticker,
        companyName = this.companyName,
        purchaseAmount = this.purchaseAmount,
        saleAmount = this.saleAmount,
    )
}
