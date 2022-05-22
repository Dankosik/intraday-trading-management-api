package ru.dankos.api.intradaytradingmanagement.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.dankos.api.intradaytradingmanagement.controller.DealController.Companion.ROOT_PATH
import ru.dankos.api.intradaytradingmanagement.dto.DealRequest
import ru.dankos.api.intradaytradingmanagement.dto.DealResponse
import ru.dankos.api.intradaytradingmanagement.dto.DealsRequest
import ru.dankos.api.intradaytradingmanagement.service.DealService

@RestController
@RequestMapping(ROOT_PATH)
class DealController(
    val dealService: DealService
) {

    @GetMapping("/{id}")
    fun getDealById(@PathVariable id: String): DealResponse = dealService.getDealById(id)

    @GetMapping
    fun getDeals(
        @RequestParam(required = false) companyName: String?,
        @RequestParam(required = false) ticker: String?,
        @RequestParam(required = false) date: String?,
    ): List<DealResponse> = dealService.getDeals(
        DealsRequest(
            companyName = companyName,
            date = date,
            ticker = ticker
        )
    )

    @PostMapping
    fun createDeal(@RequestBody deal: DealRequest): DealResponse = dealService.createDeal(deal)

    @PutMapping("{id}")
    fun updateDeal(@PathVariable id: String, @RequestBody deal: DealRequest): DealResponse =
        dealService.updateDeal(id, deal)

    companion object {

        const val ROOT_PATH = "/deals"
    }
}
