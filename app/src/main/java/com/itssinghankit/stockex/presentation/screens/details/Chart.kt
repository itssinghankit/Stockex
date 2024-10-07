package com.itssinghankit.stockex.presentation.screens.details

import com.itssinghankit.stockex.domain.model.details.ChartModel
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer

data class GraphData(
    val isGraphLoading: Boolean = false,
    val selectedChart: ChartType = ChartType.DAY,
    val selectedChartData: StockChartData? = null,
    val day: List<ChartModel>? = null,
    val week: List<ChartModel>? = null,
    val month: List<ChartModel>? = null,
    val oneYear: List<ChartModel>? = null,
    val fiveYear: List<ChartModel>? = null,
    val all: List<ChartModel>? = null,
)

data class StockChartData(
    val markerLabels: List<String>,
    val charModelProducer: CartesianChartModelProducer
)

enum class ChartType {
    DAY,
    WEEK,
    MONTH,
    ONE_YEAR,
    FIVE_YEAR,
    ALL
}