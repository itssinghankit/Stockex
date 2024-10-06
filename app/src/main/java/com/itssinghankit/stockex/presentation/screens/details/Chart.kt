package com.itssinghankit.stockex.presentation.screens.details

import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer

data class GraphData(
    val isGraphLoading: Boolean = false,
    val selectedChart: ChartType = ChartType.DAY,
    val selectedChartData: StockChartData? = null,
    val day: StockChartData? = null,
    val week: StockChartData? = null,
    val month: StockChartData? = null,
    val oneYear: StockChartData? = null,
    val fiveYear: StockChartData? = null,
    val all: StockChartData? = null,
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