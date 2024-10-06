package com.itssinghankit.stockex.data.repository

import com.example.sharesphere.util.ApiResult
import com.example.sharesphere.util.DataError
import com.itssinghankit.stockex.data.mapper.toChartModelListDto
import com.itssinghankit.stockex.data.mapper.toDetailPricePercentModel
import com.itssinghankit.stockex.data.mapper.toDetailsModel
import com.itssinghankit.stockex.data.mapper.toGainersLoosersModel
import com.itssinghankit.stockex.data.mapper.toSearchModelList
import com.itssinghankit.stockex.data.remote.AlphaVantageApi
import com.itssinghankit.stockex.data.remote.StockexApi
import com.itssinghankit.stockex.domain.model.details.ChartModel
import com.itssinghankit.stockex.domain.model.details.DetailsModel
import com.itssinghankit.stockex.domain.model.details.DetailsPricePercentModel
import com.itssinghankit.stockex.domain.model.home.GetGainersLoosersModel
import com.itssinghankit.stockex.domain.model.search.SearchModel
import com.itssinghankit.stockex.domain.repository.RepositoryInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class RepositoryImplementation(
    private val stockexApi: StockexApi,
    private val alphaVantageApi: AlphaVantageApi
) : RepositoryInterface {
    override suspend fun search(searchQuery: String): Flow<ApiResult<List<SearchModel>, DataError.Network>> =
        flow {
            try {
                val response = stockexApi.search(searchQuery = searchQuery).toSearchModelList()
                emit(ApiResult.Success(response))
            } catch (e: HttpException) {
                when (e.code()) {
                    400 -> emit(ApiResult.Error(DataError.Network.BAD_REQUEST))
                    404 -> emit(ApiResult.Error(DataError.Network.NOT_FOUND))
                    else -> emit(ApiResult.Error(DataError.Network.INTERNAL_SERVER_ERROR))
                }
            } catch (e: IOException) {
                emit(ApiResult.Error(DataError.Network.UNKNOWN))
            }
        }

    override suspend fun getDetails(symbol: String): Flow<ApiResult<DetailsModel, DataError.Network>> =
        flow {
            try {
                val response = alphaVantageApi.getDetails().toDetailsModel()
                emit(ApiResult.Success(response))
            } catch (e: HttpException) {
                when (e.code()) {
                    400 -> emit(ApiResult.Error(DataError.Network.BAD_REQUEST))
                    404 -> emit(ApiResult.Error(DataError.Network.NOT_FOUND))
                    else -> emit(ApiResult.Error(DataError.Network.INTERNAL_SERVER_ERROR))
                }
            } catch (e: IOException) {
                emit(ApiResult.Error(DataError.Network.UNKNOWN))
            }
        }

    override suspend fun getPricePercentDetails(symbol: String): Flow<ApiResult<DetailsPricePercentModel, DataError.Network>> =
        flow {
            try {
                val response = alphaVantageApi.getPricePercentDetails().toDetailPricePercentModel()
                emit(ApiResult.Success(response))
            } catch (e: HttpException) {
                when (e.code()) {
                    400 -> emit(ApiResult.Error(DataError.Network.BAD_REQUEST))
                    404 -> emit(ApiResult.Error(DataError.Network.NOT_FOUND))
                    else -> emit(ApiResult.Error(DataError.Network.INTERNAL_SERVER_ERROR))
                }
            } catch (e: IOException) {
                emit(ApiResult.Error(DataError.Network.UNKNOWN))
            }
        }

    override suspend fun getGainersLoosers(): Flow<ApiResult<GetGainersLoosersModel, DataError.Network>> =
        flow {
            try {
                val response = alphaVantageApi.getGainersLoosers().toGainersLoosersModel()
                emit(ApiResult.Success(response))
            } catch (e: HttpException) {
                when (e.code()) {
                    400 -> emit(ApiResult.Error(DataError.Network.BAD_REQUEST))
                    404 -> emit(ApiResult.Error(DataError.Network.NOT_FOUND))
                    else -> emit(ApiResult.Error(DataError.Network.INTERNAL_SERVER_ERROR))
                }
            } catch (e: IOException) {
                emit(ApiResult.Error(DataError.Network.UNKNOWN))
            }
        }

    override suspend fun getDayGraphData(symbol: String): Flow<ApiResult<List<ChartModel>, DataError.Network>> =
        flow {
            try {
                val response = alphaVantageApi.getDayData().toChartModelListDto()
                emit(ApiResult.Success(response))
            } catch (e: HttpException) {
                when (e.code()) {
                    400 -> emit(ApiResult.Error(DataError.Network.BAD_REQUEST))
                    404 -> emit(ApiResult.Error(DataError.Network.NOT_FOUND))
                    else -> emit(ApiResult.Error(DataError.Network.INTERNAL_SERVER_ERROR))
                }
            } catch (e: IOException) {
                emit(ApiResult.Error(DataError.Network.UNKNOWN))
            }
        }

    override suspend fun getWeekGraphData(symbol: String): Flow<ApiResult<List<ChartModel>, DataError.Network>> =
        flow {
            try {
                val response = alphaVantageApi.getWeekData().toChartModelListDto()
                emit(ApiResult.Success(response))
            } catch (e: HttpException) {
                when (e.code()) {
                    400 -> emit(ApiResult.Error(DataError.Network.BAD_REQUEST))
                    404 -> emit(ApiResult.Error(DataError.Network.NOT_FOUND))
                    else -> emit(ApiResult.Error(DataError.Network.INTERNAL_SERVER_ERROR))
                }
            } catch (e: IOException) {
                emit(ApiResult.Error(DataError.Network.UNKNOWN))
            }
        }

    override suspend fun getMonthGraphData(symbol: String): Flow<ApiResult<List<ChartModel>, DataError.Network>> =
        flow {
            try {
                val response = alphaVantageApi.getMonthData().toChartModelListDto()
                emit(ApiResult.Success(response))
            } catch (e: HttpException) {
                when (e.code()) {
                    400 -> emit(ApiResult.Error(DataError.Network.BAD_REQUEST))
                    404 -> emit(ApiResult.Error(DataError.Network.NOT_FOUND))
                    else -> emit(ApiResult.Error(DataError.Network.INTERNAL_SERVER_ERROR))
                }
            } catch (e: IOException) {
                emit(ApiResult.Error(DataError.Network.UNKNOWN))
            }
        }

    override suspend fun getOneYrGraphData(symbol: String): Flow<ApiResult<List<ChartModel>, DataError.Network>> =
    flow {
        try {
            val response = alphaVantageApi.getOneYearData().toChartModelListDto()
            emit(ApiResult.Success(response))
        } catch (e: HttpException) {
            when (e.code()) {
                400 -> emit(ApiResult.Error(DataError.Network.BAD_REQUEST))
                404 -> emit(ApiResult.Error(DataError.Network.NOT_FOUND))
                else -> emit(ApiResult.Error(DataError.Network.INTERNAL_SERVER_ERROR))
            }
        } catch (e: IOException) {
            emit(ApiResult.Error(DataError.Network.UNKNOWN))
        }
    }

    override suspend fun getFiveYrGraphData(symbol: String): Flow<ApiResult<List<ChartModel>, DataError.Network>> =
        flow {
            try {
                val response = alphaVantageApi.getFiveYearData().toChartModelListDto()
                emit(ApiResult.Success(response))
            } catch (e: HttpException) {
                when (e.code()) {
                    400 -> emit(ApiResult.Error(DataError.Network.BAD_REQUEST))
                    404 -> emit(ApiResult.Error(DataError.Network.NOT_FOUND))
                    else -> emit(ApiResult.Error(DataError.Network.INTERNAL_SERVER_ERROR))
                }
            } catch (e: IOException) {
                emit(ApiResult.Error(DataError.Network.UNKNOWN))
            }
        }

    override suspend fun getAllYrGraphData(symbol: String): Flow<ApiResult<List<ChartModel>, DataError.Network>> =
        flow {
            try {
                val response = alphaVantageApi.getAllYearData().toChartModelListDto()
                emit(ApiResult.Success(response))
            } catch (e: HttpException) {
                when (e.code()) {
                    400 -> emit(ApiResult.Error(DataError.Network.BAD_REQUEST))
                    404 -> emit(ApiResult.Error(DataError.Network.NOT_FOUND))
                    else -> emit(ApiResult.Error(DataError.Network.INTERNAL_SERVER_ERROR))
                }
            } catch (e: IOException) {
                emit(ApiResult.Error(DataError.Network.UNKNOWN))
            }
        }
}