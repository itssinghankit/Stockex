package com.itssinghankit.stockex.domain.model.details

data class DetailsModel(
    val marketCap: String,
    val peRatio: String,
    val dividendYield: String,
    val eps: String,
    val bookValue: String,
    val revenue: String,
    val netIncome: String,
    val assetType: String,
    val name: String,
    val description: String,
    val exchange: String,
    val currency:String,
    val symbol:String
)
