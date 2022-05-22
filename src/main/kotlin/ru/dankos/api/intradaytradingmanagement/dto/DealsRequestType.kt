package ru.dankos.api.intradaytradingmanagement.dto

enum class DealsRequestType(val value: String) {
    GET_ALL("getAll"),
    GET_BY_TICKER("getByTicker"),
    GET_BY_COMPANY_NAME("getByCompanyName"),
    GET_BY_DATE("getByDate"),
    GET_BY_TICKER_AND_DATE("getByTickerAndDate"),
    GET_BY_COMPANY_NAME_AND_DATE("getByCompanyNameAndDate")
}
