package com.itssinghankit.stockex.presentation.screens.details

import android.content.Context
import androidx.compose.runtime.remember
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.sharesphere.util.ApiResult
import com.example.sharesphere.util.DataError
import com.itssinghankit.stockex.R
import com.itssinghankit.stockex.domain.model.details.ChartModel
import com.itssinghankit.stockex.domain.model.details.DetailsModel
import com.itssinghankit.stockex.domain.use_case.details.GetStockDetailsUseCase
import com.itssinghankit.stockex.presentation.navigation.ScreenSealedClass
import com.itssinghankit.stockex.util.NetworkMonitor
import com.itssinghankit.stockex.util.UiText
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModel
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class DetailsStates(
    val isLoading: Boolean = false,
    val errorMessage: UiText? = null,
    val symbol: String? = null,
    val fundamentalData: DetailsModel? = null,
    val chartData: StockChartData?=null
)

@HiltViewModel
class DetailsViewModel @Inject constructor(
    networkMonitor: NetworkMonitor,
    private val savedStateHandle: SavedStateHandle,
    private val getStockDetailsUseCase: GetStockDetailsUseCase
) : ViewModel() {

    private val _states = MutableStateFlow(DetailsStates())
    val states: StateFlow<DetailsStates> = _states

    val networkState = networkMonitor.networkState

    init {
        _states.update {
            it.copy(
                symbol = savedStateHandle.toRoute<ScreenSealedClass.DetailsScreen>().symbol
            )
        }
        getDetails()
        createChartData()
    }


    fun onEvent(event: DetailsEvents) {

        when (event) {
            DetailsEvents.ResetErrorMessage -> {
                _states.update {
                    it.copy(errorMessage = null)
                }
            }
        }
    }

    private fun getDetails() {

        viewModelScope.launch(Dispatchers.IO) {
            _states.update {
                it.copy(isLoading = true)
            }

            getStockDetailsUseCase(states.value.symbol ?: "IBM").collect { result ->
                when (result) {
                    is ApiResult.Error -> {

                        when (result.error) {
                            DataError.Network.INTERNAL_SERVER_ERROR -> {
                                withContext(Dispatchers.Main) {
                                    _states.update {
                                        it.copy(
                                            errorMessage = UiText.StringResource(R.string.errorInternalServerError),
                                            isLoading = false
                                        )
                                    }
                                }
                            }

                            else -> {
                                withContext(Dispatchers.Main) {
                                    _states.update {
                                        it.copy(
                                            errorMessage = UiText.StringResource(R.string.errorCheckInternet),
                                            isLoading = false
                                        )
                                    }
                                }
                            }
                        }
                    }

                    is ApiResult.Success -> {
                        withContext(Dispatchers.Main) {
                            _states.update {
                                it.copy(
                                    isLoading = false,
                                    fundamentalData = result.data
                                )
                            }
                        }
                    }
                }
            }
        }


    }

    fun createChartData(){

        val data = listOf(
            ChartModel("a",3f),
            ChartModel("b",4f),
            ChartModel("c",6f),
            ChartModel("d",3f),
            ChartModel("e",9f),
            ChartModel("f",5f),
            ChartModel("g",3f),
            ChartModel("h",5f),
            ChartModel("i",7f),
            ChartModel("j",9f),
        )

        val modelProducer = CartesianChartModelProducer()

        val labels = data.map { it.label }
        val formattedLabels = CartesianValueFormatter { _, index ,_->
            labels[index.toInt()]
        }

        val xAxisValues = data.mapIndexed { index, chartModel -> index.toFloat() }
        val yAxisValues = data.map { it.value }

        viewModelScope.launch(Dispatchers.IO) {
            modelProducer.runTransaction {
                lineSeries { series(xAxisValues,yAxisValues) }
            }

        }
        _states.update {
            it.copy(chartData = StockChartData(formattedLabels,modelProducer))
        }


    }


}