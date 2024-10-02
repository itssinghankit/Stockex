package com.itssinghankit.stockex.presentation.navigation

import androidx.navigation.NavHostController

class Navigator(private val navController: NavHostController) {
    fun onAction(action: NavigationActions) {
        when (action) {
            NavigationActions.NavigateBack -> navController.popBackStack()
        }
    }

}