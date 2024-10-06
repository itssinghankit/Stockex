package com.itssinghankit.stockex.data.remote.dto.details.one_year

import com.google.gson.annotations.SerializedName

data class OneYrGraphDto(
    @SerializedName("Meta Data")
    val metaData: MetaData,
    @SerializedName("Weekly Time Series")
    val timeSeries: Map<String, TimeSeriesEntry>
)

data class MetaData(
    @SerializedName("1. Information")
    val information: String,
    @SerializedName("2. Symbol")
    val symbol: String,
    @SerializedName("3. Last Refreshed")
    val lastRefreshed: String,
    @SerializedName("6. Time Zone")
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