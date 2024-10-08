package com.itssinghankit.stockex.data.remote

import com.itssinghankit.stockex.BuildConfig
import com.itssinghankit.stockex.data.remote.dto.details.DetailsPricePercentDto
import com.itssinghankit.stockex.data.remote.dto.details.DetailsResDto
import com.itssinghankit.stockex.data.remote.dto.details.all.AllYrGraphDto
import com.itssinghankit.stockex.data.remote.dto.details.day.DayGraphDto
import com.itssinghankit.stockex.data.remote.dto.details.five_year.FiveYrGraphDto
import com.itssinghankit.stockex.data.remote.dto.details.month.MonthGraphDto
import com.itssinghankit.stockex.data.remote.dto.details.one_year.OneYrGraphDto
import com.itssinghankit.stockex.data.remote.dto.details.week.WeekGraphDto
import com.itssinghankit.stockex.data.remote.dto.home.GainersLoosersDto
import retrofit2.http.GET
import retrofit2.http.Query

interface AlphaVantageApi {
    @GET("query")
    suspend fun getDetails(
        @Query("function") function: String = "OVERVIEW",
        @Query("symbol") symbol: String,
        @Query("apikey") apikey: String = BuildConfig.ALPHA_VANTAGE_API_KEY
    ): DetailsResDto

    @GET("query")
    suspend fun getPricePercentDetails(
        @Query("function") function: String = "GLOBAL_QUOTE",
        @Query("symbol") symbol: String,
        @Query("apikey") apikey: String = BuildConfig.ALPHA_VANTAGE_API_KEY,
    ): DetailsPricePercentDto

    @GET("query")
    suspend fun getGainersLoosers(
        @Query("function") function: String = "TOP_GAINERS_LOSERS",
        @Query("apikey") apikey: String = BuildConfig.ALPHA_VANTAGE_API_KEY
    ): GainersLoosersDto

    @GET("query")
    suspend fun getDayData(
        @Query("function") function: String = "TIME_SERIES_INTRADAY",
        @Query("symbol") symbol: String,
        @Query("interval") interval: String = "1min",
        @Query("apikey") apikey: String = BuildConfig.ALPHA_VANTAGE_API_KEY
    ): DayGraphDto

    @GET("query")
    suspend fun getWeekData(
        @Query("function") function: String = "TIME_SERIES_INTRADAY",
        @Query("symbol") symbol: String,
        @Query("interval") interval: String = "5min",
        @Query("outputsize") outputsize: String = "full",
        @Query("apikey") apikey: String = BuildConfig.ALPHA_VANTAGE_API_KEY,
    ): WeekGraphDto

    @GET("query")
    suspend fun getMonthData(
        @Query("function") function: String = "TIME_SERIES_INTRADAY",
        @Query("symbol") symbol: String,
        @Query("interval") interval: String = "15min",
        @Query("outputsize") outputsize: String = "full",
        @Query("apikey") apikey: String = BuildConfig.ALPHA_VANTAGE_API_KEY,
    ): MonthGraphDto

    @GET("query")
    suspend fun getOneYearData(
        @Query("function") function: String = "TIME_SERIES_WEEKLY",
        @Query("symbol") symbol: String,
        @Query("apikey") apikey: String = BuildConfig.ALPHA_VANTAGE_API_KEY,
    ): OneYrGraphDto

    @GET("query")
    suspend fun getFiveYearData(
        @Query("function") function: String = "TIME_SERIES_MONTHLY",
        @Query("symbol") symbol: String,
        @Query("apikey") apikey: String = BuildConfig.ALPHA_VANTAGE_API_KEY,
    ): FiveYrGraphDto

    @GET("query")
    suspend fun getAllYearData(
        @Query("function") function: String = "TIME_SERIES_MONTHLY",
        @Query("symbol") symbol: String,
        @Query("apikey") apikey: String = BuildConfig.ALPHA_VANTAGE_API_KEY,
    ): AllYrGraphDto

}