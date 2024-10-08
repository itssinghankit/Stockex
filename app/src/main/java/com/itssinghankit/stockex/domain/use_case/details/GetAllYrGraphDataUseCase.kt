package com.itssinghankit.stockex.domain.use_case.details

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.sharesphere.util.ApiResult
import com.example.sharesphere.util.DataError
import com.itssinghankit.stockex.domain.model.details.ChartModel
import com.itssinghankit.stockex.domain.repository.RepositoryInterface
import com.itssinghankit.stockex.util.dateToFullDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllYrGraphDataUseCase @Inject constructor(
    private val repositoryInterface: RepositoryInterface
) {
    @RequiresApi(Build.VERSION_CODES.O)
    suspend operator fun invoke(symbol: String): Flow<ApiResult<List<ChartModel>, DataError.Network>> =
        flow {
            val response = repositoryInterface.getAllYrGraphData(symbol)
            response.collect { result ->
                when (result) {
                    is ApiResult.Error -> emit(ApiResult.Error(result.error))
                    is ApiResult.Success -> {
                        val data = result.data.map {
                            val outputLabel =
                                "$${String.format("%.2f", it.value)} | ${dateToFullDate(it.label)}"
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