package ru.dankos.api.intradaytradingmanagement.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.dankos.api.intradaytradingmanagement.controller.DealController.Companion.ROOT_PATH
import ru.dankos.api.intradaytradingmanagement.model.Deal
import ru.dankos.api.intradaytradingmanagement.service.DealService

@RestController
@RequestMapping(ROOT_PATH)
class DealController(
    val dealService: DealService
) {

    @GetMapping("/{id}")
    fun getDealById(@PathVariable id: String): Deal = dealService.getDealById(id)

    @GetMapping
    fun getDeals(
        @RequestParam customQuery: Map<String, String>
    ): List<Deal> = if (customQuery.isEmpty()) {
        dealService.getAllDeals()
    } else dealService.getDealsByCustomQuery(customQuery)

    @PostMapping
    fun createDeal(@RequestBody deal: Deal): Deal = dealService.createDeal(deal)

    companion object {
        const val ROOT_PATH = "/deals"
    }
}
