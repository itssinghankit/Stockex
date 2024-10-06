package com.itssinghankit.stockex.presentation.screens.details

sealed class DetailsEvents {
    object ResetErrorMessage : DetailsEvents()
    data class OnSelectionChart(val chartType: ChartType):DetailsEvents()
}