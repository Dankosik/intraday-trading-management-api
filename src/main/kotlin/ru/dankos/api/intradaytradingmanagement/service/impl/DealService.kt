package ru.dankos.api.intradaytradingmanagement.service.impl

import org.springframework.stereotype.Service
import ru.dankos.api.intradaytradingmanagement.controller.queryparams.DealQueryParams
import ru.dankos.api.intradaytradingmanagement.dto.DealRequest
import ru.dankos.api.intradaytradingmanagement.dto.DealResponse
import ru.dankos.api.intradaytradingmanagement.model.Deal
import ru.dankos.api.intradaytradingmanagement.repository.DealRepository
import ru.dankos.api.intradaytradingmanagement.service.exception.EntityNotFoundException
import ru.dankos.api.intradaytradingmanagement.service.exception.NotSupportedNumberQueryParamException
import ru.dankos.api.intradaytradingmanagement.service.exception.NotSupportedQueryParamException
import java.time.LocalDate

@Service
class DealService(
    val dealRepository: DealRepository
) {

    fun getDealsByDate(date: LocalDate): List<DealResponse> =
        dealRepository.findDealsByDate(date.atStartOfDay()).map { it.toDealResponse() }

    fun getDealById(id: String): DealResponse = dealRepository.findById(id).map { it.toDealResponse() }
        .orElseThrow { EntityNotFoundException("The Deal with id: $id is not found") }

    fun getAllDeals(): List<DealResponse> = dealRepository.findAll().map { it.toDealResponse() }

    fun getDealsByTicker(ticker: String): List<DealResponse> =
        dealRepository.findDealsByTicker(ticker).map { it.toDealResponse() }

    fun getDealsByCompanyName(companyName: String): List<DealResponse> =
        dealRepository.findDealsByCompanyName(companyName).map { it.toDealResponse() }

    fun createDeal(dealRequest: DealRequest): DealResponse =
        dealRepository.save(dealRequest.toEntity()).toDealResponse()

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

    fun getDealsWithQueryParams(queryParams: Map<String, String>): List<DealResponse> {
        if (queryParams.size > 1) throw NotSupportedNumberQueryParamException(
            "Supported number of query parameters: '1', but actual is: '${queryParams.size}'"
        )
        return queryParams.entries.first()
            .let {
                when (it.key) {
                    DealQueryParams.COMPANY_NAME.value -> getDealsByCompanyName(it.value)
                    DealQueryParams.TICKER.value -> getDealsByTicker(it.value)
                    DealQueryParams.DATE.value -> getDealsByDate(LocalDate.parse(it.value))
                    else -> throw NotSupportedQueryParamException("Not supported query parameter: ${queryParams.keys}")
                }
            }
    }
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
