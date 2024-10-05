package com.itssinghankit.stockex.data.remote.dto.home

data class GainersLoosersDto(
    val last_updated: String,
    val metadata: String,
    val most_actively_traded: List<MostActivelyTraded>,
    val top_gainers: List<TopGainer>,
    val top_losers: List<TopLoser>
)