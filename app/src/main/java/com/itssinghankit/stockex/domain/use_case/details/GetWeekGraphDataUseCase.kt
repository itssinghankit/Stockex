package com.itssinghankit.stockex.domain.use_case.details

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.sharesphere.util.ApiResult
import com.example.sharesphere.util.DataError
import com.itssinghankit.stockex.domain.model.details.ChartModel
import com.itssinghankit.stockex.domain.repository.RepositoryInterface
import com.itssinghankit.stockex.presentation.screens.details.ChartType
import com.itssinghankit.stockex.util.DateTimePatterns
import com.itssinghankit.stockex.util.dateTimeToDateTime
import com.itssinghankit.stockex.util.filterChartItems
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class GetWeekGraphDataUseCase @Inject constructor(
    private val repositoryInterface: RepositoryInterface
) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend operator fun invoke(symbol: String): Flow<ApiResult<List<ChartModel>, DataError.Network>> =
        flow {
            val response = repositoryInterface.getWeekGraphData(symbol)
            response.collect { result ->
                when (result) {
                    is ApiResult.Error -> emit(ApiResult.Error(result.error))
                    is ApiResult.Success -> {
                        val data = result.data.filter {
                            filterChartItems(
                                it.label,
                                ChartType.WEEK,
                                DateTimePatterns.INCOMING_DATE_TIME
                            )
                        }.map {
                            val outputLabel =
                                "$${String.format("%.2f", it.value)} | ${dateTimeToDateTime(it.label)}"
                            ChartModel(
                                label = outputLabel,
                                value = it.value
                            )
                        }
                        emit(ApiResult.Success(data))
                    }
                }


            }

        }
}