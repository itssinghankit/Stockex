package com.itssinghankit.stockex.data.remote

import com.itssinghankit.stockex.data.remote.dto.search.SearchResDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface StockexApi {

    @Headers("AddRapidHeader: true")
    @GET("query")
    suspend fun search(
        @Query("datatype") datatype: String = "json",
        @Query("keywords") searchQuery: String,
        @Query("function") function: String = "SYMBOL_SEARCH"

    ): SearchResDto


}