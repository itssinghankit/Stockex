package com.itssinghankit.stockex.data.remote.dto.home

data class MostActivelyTraded(
    val change_amount: String,
    val change_percentage: String,
    val price: String,
    val ticker: String,
    val volume: String
)