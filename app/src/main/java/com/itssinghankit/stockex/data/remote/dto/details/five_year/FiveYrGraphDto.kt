package com.itssinghankit.stockex.data.remote.dto.details.five_year

import com.google.gson.annotations.SerializedName

data class FiveYrGraphDto(
    @SerializedName("Meta Data")
    val metaData: MetaData,
    @SerializedName("Monthly Time Series")
    val timeSeries: Map<String, TimeSeriesEntry>
)

data class MetaData(
    @SerializedName("1. Information")
    val information: String,
    @SerializedName("2. Symbol")
    val symbol: String,
    @SerializedName("3. Last Refreshed")
    val lastRefreshed: String,
    @SerializedName("4. Time Zone")
    val timeZone: String
)

data class TimeSeriesEntry(
    @SerializedName("1. open")
    val open: String,
    @SerializedName("2. high")
    val high: String,
    @SerializedName("3. low")
    val low: String,
    @SerializedName("4. close")
    val close: String,
    @SerializedName("5. volume")
    val volume: String
)