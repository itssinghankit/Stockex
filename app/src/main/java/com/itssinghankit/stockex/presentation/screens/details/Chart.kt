package com.itssinghankit.stockex.presentation.screens.details

import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter

data class StockChartData(
    val formattedLabels: CartesianValueFormatter,
    val charModelProducer: CartesianChartModelProducer
)