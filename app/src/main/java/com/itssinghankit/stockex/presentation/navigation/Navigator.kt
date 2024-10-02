package com.itssinghankit.stockex.presentation.navigation

import androidx.navigation.NavHostController

class Navigator(private val navController: NavHostController) {
    fun onAction(action: NavigationActions) {
        when (action) {

            NavigationActions.NavigateBack -> navController.popBackStack()

            NavigationActions.NavigateToHomeScreen -> {
                navController.navigate(ScreenSealedClass.HomeScreen) {
                    popUpTo(ScreenSealedClass.SplashScreen) {
                        inclusive = true
                    }
                }
            }

        }
    }

}