package com.itssinghankit.stockex.presentation.screens.details

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.sharesphere.util.ApiResult
import com.example.sharesphere.util.DataError
import com.itssinghankit.stockex.R
import com.itssinghankit.stockex.domain.model.details.ChartModel
import com.itssinghankit.stockex.domain.model.details.DetailsModel
import com.itssinghankit.stockex.domain.model.details.DetailsPricePercentModel
import com.itssinghankit.stockex.domain.use_case.details.GetAllYrGraphDataUseCase
import com.itssinghankit.stockex.domain.use_case.details.GetDayGraphDataUseCase
import com.itssinghankit.stockex.domain.use_case.details.GetFiveYrGraphDataUseCase
import com.itssinghankit.stockex.domain.use_case.details.GetMonthGraphDataUseCase
import com.itssinghankit.stockex.domain.use_case.details.GetOneYrGraphDataUseCase
import com.itssinghankit.stockex.domain.use_case.details.GetStockDetailsUseCase
import com.itssinghankit.stockex.domain.use_case.details.GetStockPricePercentDetailsUseCase
import com.itssinghankit.stockex.domain.use_case.details.GetWeekGraphDataUseCase
import com.itssinghankit.stockex.presentation.navigation.ScreenSealedClass
import com.itssinghankit.stockex.util.NetworkMonitor
import com.itssinghankit.stockex.util.UiText
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

data class DetailsStates(
    val isLoading: Boolean = false,
    val errorMessage: UiText? = null,
    val symbol: String? = null,
    val fundamentalData: DetailsModel? = null,
    val pricePercentData:DetailsPricePercentModel?=null
)

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class DetailsViewModel @Inject constructor(
    networkMonitor: NetworkMonitor,
    private val savedStateHandle: SavedStateHandle,
    private val getStockDetailsUseCase: GetStockDetailsUseCase,
    private val getStockPricePercentDetailsUseCase: GetStockPricePercentDetailsUseCase,
    private val getDayGraphDataUseCase: GetDayGraphDataUseCase,
    private val getWeekGraphDataUseCase: GetWeekGraphDataUseCase,
    private val getMonthGraphDataUseCase: GetMonthGraphDataUseCase,
    private val getOneYrGraphDataUseCase: GetOneYrGraphDataUseCase,
    private val getFiveYrGraphDataUseCase: GetFiveYrGraphDataUseCase,
    private val getAllYrGraphDataUseCase: GetAllYrGraphDataUseCase
) : ViewModel() {

    private val _states = MutableStateFlow(DetailsStates())
    val states: StateFlow<DetailsStates> = _states

    private val _graphData = MutableStateFlow(GraphData())
    val graphData: StateFlow<GraphData> = _graphData

    private val modelProducer = CartesianChartModelProducer()

    val networkState = networkMonitor.networkState

    init {
        _states.update {
            it.copy(
                symbol = savedStateHandle.toRoute<ScreenSealedClass.DetailsScreen>().symbol
            )
        }
        getDetails()
        getPricePercentDetails()
        initializeGraph()
        getDayChartData()
    }


    fun onEvent(event: DetailsEvents) {

        when (event) {
            DetailsEvents.ResetErrorMessage -> {
                _states.update {
                    it.copy(errorMessage = null)
                }
            }

            is DetailsEvents.OnSelectionChart -> {
                Timber.d("month: ${_graphData.value.month}")
                Timber.d("year: ${_graphData.value.oneYear}")

                when (event.chartType) {
                    ChartType.DAY -> {
                        _graphData.update { it.copy(selectedChart = ChartType.DAY) }
                        if (_graphData.value.day == null) {
                            getDayChartData()
                        } else {
                            createChartData(_graphData.value.day!!,ChartType.DAY)
                        }
                    }

                    ChartType.WEEK -> {
                        _graphData.update { it.copy(selectedChart = ChartType.WEEK) }
                        if (_graphData.value.week == null) {
                            getWeekChartData()
                        } else {
                            createChartData(_graphData.value.week!!,ChartType.WEEK)
                        }
                    }

                    ChartType.MONTH -> {
                        _graphData.update { it.copy(selectedChart = ChartType.MONTH) }
                        if (_graphData.value.month == null) {
                           getMonthChartData()
                        } else {
                            createChartData(_graphData.value.month!!,ChartType.MONTH)
                        }
                    }

                    ChartType.ONE_YEAR -> {
                        _graphData.update { it.copy(selectedChart = ChartType.ONE_YEAR) }
                        if (_graphData.value.oneYear == null) {
                            getOneYrChartData()
                        } else {
                            createChartData(_graphData.value.oneYear!!,ChartType.ONE_YEAR)
                        }
                    }

                    ChartType.FIVE_YEAR -> {
                        _graphData.update { it.copy(selectedChart = ChartType.FIVE_YEAR) }
                        if (_graphData.value.fiveYear == null) {
                            getFiveYrChartData()
                        } else {
                            createChartData(_graphData.value.fiveYear!!,ChartType.FIVE_YEAR)
                        }
                    }

                    ChartType.ALL -> {
                        _graphData.update { it.copy(selectedChart = ChartType.ALL) }
                        if (_graphData.value.all == null) {
                            getAllYrChartData()
                        } else {
                            createChartData(_graphData.value.all!!,ChartType.ALL)
                        }
                    }
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
                            DataError.Network.API_LIMIT_EXCEEDED -> {
                                withContext(Dispatchers.Main) {
                                    _states.update {
                                        it.copy(
                                            errorMessage = UiText.StringResource(R.string.errorApiLimit),
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

    private fun getPricePercentDetails() {

        viewModelScope.launch(Dispatchers.IO) {
            _states.update {
                it.copy(isLoading = true)
            }

            getStockPricePercentDetailsUseCase(states.value.symbol ?: "IBM").collect { result ->
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
                            DataError.Network.API_LIMIT_EXCEEDED -> {
                                withContext(Dispatchers.Main) {
                                    _states.update {
                                        it.copy(
                                            errorMessage = UiText.StringResource(R.string.errorApiLimit),
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
                                    pricePercentData = result.data
                                )
                            }
                        }
                    }
                }
            }
        }


    }

    private fun initializeGraph() {
        _graphData.update {
            it.copy(selectedChartData = StockChartData(emptyList(), modelProducer))
        }
    }

    private fun createChartData(data: List<ChartModel>, chartType: ChartType) {

        val labels = data.map { it.label }

        val xAxisValues = data.mapIndexed { index, _ -> index.toFloat() }
//        val xAxisValues = List(data.size) { index -> index.toFloat() }
        val yAxisValues = data.map { it.value }

        viewModelScope.launch(Dispatchers.IO) {
            modelProducer.runTransaction {
                lineSeries { series(xAxisValues, yAxisValues) }
            }
        }
        _graphData.update {
            it.copy(
                selectedChartData = StockChartData(labels, modelProducer),
                isGraphLoading = false
            )
        }
        when(chartType){
            ChartType.DAY -> {
                _graphData.update {
                    it.copy(
                        selectedChartData = StockChartData(labels, modelProducer),
                        isGraphLoading = false,
                    )
                }
            }
            ChartType.WEEK -> {
                _graphData.update {
                    it.copy(
                        selectedChartData = StockChartData(labels, modelProducer),
                        isGraphLoading = false,
                    )
                }
            }
            ChartType.MONTH -> {
                _graphData.update {
                    it.copy(
                        selectedChartData = StockChartData(labels, modelProducer),
                        isGraphLoading = false,
                    )
                }
            }
            ChartType.ONE_YEAR -> {
                _graphData.update {
                    it.copy(
                        selectedChartData = StockChartData(labels, modelProducer),
                        isGraphLoading = false,
                    )
                }
            }
            ChartType.FIVE_YEAR -> {
                _graphData.update {
                    it.copy(
                        selectedChartData = StockChartData(labels, modelProducer),
                        isGraphLoading = false,
                    )
                }
            }
            ChartType.ALL -> {
                _graphData.update {
                    it.copy(
                        selectedChartData = StockChartData(labels, modelProducer),
                        isGraphLoading = false,
                    )
                }
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDayChartData() {
        viewModelScope.launch(Dispatchers.IO) {
            _graphData.update {
                it.copy(isGraphLoading = true)
            }

            getDayGraphDataUseCase(states.value.symbol ?: "IBM").collect { result ->
                when (result) {
                    is ApiResult.Error -> {

                        when (result.error) {
                            DataError.Network.INTERNAL_SERVER_ERROR -> {
                                withContext(Dispatchers.Main) {
                                    _states.update {
                                        it.copy(
                                            errorMessage = UiText.StringResource(R.string.errorInternalServerError)
                                        )
                                    }
                                    _graphData.update {
                                        it.copy(isGraphLoading = false)
                                    }
                                }
                            }
                            DataError.Network.API_LIMIT_EXCEEDED -> {
                                withContext(Dispatchers.Main) {
                                    _states.update {
                                        it.copy(
                                            errorMessage = UiText.StringResource(R.string.errorApiLimit),
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
                                        )
                                    }
                                    _graphData.update {
                                        it.copy(isGraphLoading = false)
                                    }
                                }
                            }
                        }
                    }

                    is ApiResult.Success -> {
                        withContext(Dispatchers.Main) {
                            _graphData.update {
                                it.copy(
                                    day = result.data
                                )
                            }
                            createChartData(result.data,ChartType.DAY)
                        }
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getWeekChartData() {
        viewModelScope.launch(Dispatchers.IO) {
            _graphData.update {
                it.copy(isGraphLoading = true)
            }

            getWeekGraphDataUseCase(states.value.symbol ?: "IBM").collect { result ->
                when (result) {
                    is ApiResult.Error -> {

                        when (result.error) {
                            DataError.Network.INTERNAL_SERVER_ERROR -> {
                                withContext(Dispatchers.Main) {
                                    _states.update {
                                        it.copy(
                                            errorMessage = UiText.StringResource(R.string.errorInternalServerError)
                                        )
                                    }
                                    _graphData.update {
                                        it.copy(isGraphLoading = false)
                                    }
                                }
                            }
                            DataError.Network.API_LIMIT_EXCEEDED -> {
                                withContext(Dispatchers.Main) {
                                    _states.update {
                                        it.copy(
                                            errorMessage = UiText.StringResource(R.string.errorApiLimit),
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
                                        )
                                    }
                                    _graphData.update {
                                        it.copy(isGraphLoading = false)
                                    }
                                }
                            }
                        }
                    }

                    is ApiResult.Success -> {
                        withContext(Dispatchers.Main) {
                            _graphData.update {
                                it.copy(
                                    week = result.data
                                )
                            }
                            createChartData(result.data,ChartType.WEEK)
                        }
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getMonthChartData() {
        viewModelScope.launch(Dispatchers.IO) {
            _graphData.update {
                it.copy(isGraphLoading = true)
            }

            getMonthGraphDataUseCase(states.value.symbol ?: "IBM").collect { result ->
                when (result) {
                    is ApiResult.Error -> {

                        when (result.error) {
                            DataError.Network.INTERNAL_SERVER_ERROR -> {
                                withContext(Dispatchers.Main) {
                                    _states.update {
                                        it.copy(
                                            errorMessage = UiText.StringResource(R.string.errorInternalServerError)
                                        )
                                    }
                                    _graphData.update {
                                        it.copy(isGraphLoading = false)
                                    }
                                }
                            }
                            DataError.Network.API_LIMIT_EXCEEDED -> {
                                withContext(Dispatchers.Main) {
                                    _states.update {
                                        it.copy(
                                            errorMessage = UiText.StringResource(R.string.errorApiLimit),
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
                                        )
                                    }
                                    _graphData.update {
                                        it.copy(isGraphLoading = false)
                                    }
                                }
                            }
                        }
                    }

                    is ApiResult.Success -> {
                        withContext(Dispatchers.Main) {
                            _graphData.update {
                                it.copy(
                                    month = result.data
                                )
                            }
                            createChartData(result.data,ChartType.MONTH)
                        }
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getOneYrChartData() {
        viewModelScope.launch(Dispatchers.IO) {
            _graphData.update {
                it.copy(isGraphLoading = true)
            }

            getOneYrGraphDataUseCase(states.value.symbol ?: "IBM").collect { result ->
                when (result) {
                    is ApiResult.Error -> {

                        when (result.error) {
                            DataError.Network.INTERNAL_SERVER_ERROR -> {
                                withContext(Dispatchers.Main) {
                                    _states.update {
                                        it.copy(
                                            errorMessage = UiText.StringResource(R.string.errorInternalServerError)
                                        )
                                    }
                                    _graphData.update {
                                        it.copy(isGraphLoading = false)
                                    }
                                }
                            }
                            DataError.Network.API_LIMIT_EXCEEDED -> {
                                withContext(Dispatchers.Main) {
                                    _states.update {
                                        it.copy(
                                            errorMessage = UiText.StringResource(R.string.errorApiLimit),
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
                                        )
                                    }
                                    _graphData.update {
                                        it.copy(isGraphLoading = false)
                                    }
                                }
                            }
                        }
                    }

                    is ApiResult.Success -> {
                        withContext(Dispatchers.Main) {
                            _graphData.update {
                                it.copy(
                                    oneYear = result.data
                                )
                            }
                            createChartData(result.data,ChartType.ONE_YEAR)
                        }
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getFiveYrChartData() {
        viewModelScope.launch(Dispatchers.IO) {
            _graphData.update {
                it.copy(isGraphLoading = true)
            }

            getFiveYrGraphDataUseCase(states.value.symbol ?: "IBM").collect { result ->
                when (result) {
                    is ApiResult.Error -> {

                        when (result.error) {
                            DataError.Network.INTERNAL_SERVER_ERROR -> {
                                withContext(Dispatchers.Main) {
                                    _states.update {
                                        it.copy(
                                            errorMessage = UiText.StringResource(R.string.errorInternalServerError)
                                        )
                                    }
                                    _graphData.update {
                                        it.copy(isGraphLoading = false)
                                    }
                                }
                            }
                            DataError.Network.API_LIMIT_EXCEEDED -> {
                                withContext(Dispatchers.Main) {
                                    _states.update {
                                        it.copy(
                                            errorMessage = UiText.StringResource(R.string.errorApiLimit),
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
                                        )
                                    }
                                    _graphData.update {
                                        it.copy(isGraphLoading = false)
                                    }
                                }
                            }
                        }
                    }

                    is ApiResult.Success -> {
                        withContext(Dispatchers.Main) {
                            _graphData.update {
                                it.copy(
                                    fiveYear = result.data
                                )
                            }
                            createChartData(result.data,ChartType.FIVE_YEAR)
                        }
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getAllYrChartData() {
        viewModelScope.launch(Dispatchers.IO) {
            _graphData.update {
                it.copy(isGraphLoading = true)
            }

            getAllYrGraphDataUseCase(states.value.symbol ?: "IBM").collect { result ->
                when (result) {
                    is ApiResult.Error -> {

                        when (result.error) {
                            DataError.Network.INTERNAL_SERVER_ERROR -> {
                                withContext(Dispatchers.Main) {
                                    _states.update {
                                        it.copy(
                                            errorMessage = UiText.StringResource(R.string.errorInternalServerError)
                                        )
                                    }
                                    _graphData.update {
                                        it.copy(isGraphLoading = false)
                                    }
                                }
                            }
                            DataError.Network.API_LIMIT_EXCEEDED -> {
                                withContext(Dispatchers.Main) {
                                    _states.update {
                                        it.copy(
                                            errorMessage = UiText.StringResource(R.string.errorApiLimit),
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
                                        )
                                    }
                                    _graphData.update {
                                        it.copy(isGraphLoading = false)
                                    }
                                }
                            }
                        }
                    }

                    is ApiResult.Success -> {
                        withContext(Dispatchers.Main) {
                            _graphData.update {
                                it.copy(
                                    all = result.data
                                )
                            }
                            createChartData(result.data,ChartType.ALL)
                        }
                    }
                }
            }
        }
    }



}