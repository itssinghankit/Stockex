package com.itssinghankit.stockex.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.itssinghankit.stockex.presentation.screens.home.HomeScreen
import com.itssinghankit.stockex.presentation.screens.home.HomeViewModel
import com.itssinghankit.stockex.presentation.screens.splash.SplashScreen
import com.itssinghankit.stockex.util.composeAnimatedSlide

@Composable
fun RootNavGraph(rootNavController: NavHostController, navigator: Navigator) {

    NavHost(
        navController = rootNavController,
        startDestination = ScreenSealedClass.SplashScreen
    ) {

        composeAnimatedSlide<ScreenSealedClass.SplashScreen> {
            SplashScreen(navigateToHomeScreen={navigator.onAction(NavigationActions.NavigateToHomeScreen)})
        }

        composeAnimatedSlide<ScreenSealedClass.HomeScreen> {
            val viewModel:HomeViewModel = hiltViewModel()
            HomeScreen(viewModel=viewModel,onEvent=viewModel::onEvent)
        }

    }
}