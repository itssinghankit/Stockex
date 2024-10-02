package com.itssinghankit.stockex.presentation.navigation

sealed class NavigationActions {

    data object NavigateBack : NavigationActions()
    data object NavigateToHomeScreen : NavigationActions()

}