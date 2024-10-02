package com.itssinghankit.stockex.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.itssinghankit.stockex.presentation.screens.splash.SplashScreen
import com.itssinghankit.stockex.util.composeAnimatedSlide

@Composable
fun RootNavGraph(rootNavController: NavHostController, navigator: Navigator) {

    NavHost(
        navController = rootNavController,
        startDestination = ScreenSealedClass.SplashScreen
    ) {

        composeAnimatedSlide<ScreenSealedClass.SplashScreen> {
            SplashScreen()
        }

    }
}