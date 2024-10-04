package com.itssinghankit.stockex.presentation.navigation

sealed class NavigationActions {

    data object NavigateBack : NavigationActions()
    data object NavigateToHomeScreen : NavigationActions()
    data object NavigateToSearchScreen :NavigationActions()
    data class NavigateToDetailsScreen(val symbol:String):NavigationActions()

}