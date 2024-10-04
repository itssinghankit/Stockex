package com.itssinghankit.stockex.domain.repository

import com.example.sharesphere.util.ApiResult
import com.example.sharesphere.util.DataError
import com.itssinghankit.stockex.domain.model.search.SearchModel
import kotlinx.coroutines.flow.Flow

interface RepositoryInterface {
    suspend fun search(searchQuery: String): Flow<ApiResult<List<SearchModel>,DataError.Network>>
}