package com.itssinghankit.stockex.data.remote

import com.itssinghankit.stockex.data.remote.dto.details.DetailsResDto
import com.itssinghankit.stockex.data.remote.dto.home.GainersLoosersDto
import retrofit2.http.GET
import retrofit2.http.Query

interface AlphaVantageApi {
    @GET("query")
    suspend fun getDetails(
        @Query("function") function: String = "OVERVIEW",
        @Query("symbol") symbol: String ="IBM",
        @Query("apikey") apikey: String = "demo"
    ): DetailsResDto

    @GET("query")
    suspend fun getGainersLoosers(
        @Query("function") function: String = "TOP_GAINERS_LOSERS",
        @Query("apikey") apikey: String = "demo"
    ): GainersLoosersDto
}