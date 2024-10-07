package com.itssinghankit.stockex.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.itssinghankit.stockex.presentation.screens.details.DetailsScreen
import com.itssinghankit.stockex.presentation.screens.details.DetailsViewModel
import com.itssinghankit.stockex.presentation.screens.home.HomeScreen
import com.itssinghankit.stockex.presentation.screens.home.HomeViewModel
import com.itssinghankit.stockex.presentation.screens.search.SearchScreen
import com.itssinghankit.stockex.presentation.screens.search.SearchViewModel
import com.itssinghankit.stockex.presentation.screens.splash.SplashScreen
import com.itssinghankit.stockex.util.composeAnimatedSlide

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RootNavGraph(rootNavController: NavHostController, navigator: Navigator) {

    NavHost(
        navController = rootNavController,
        startDestination = ScreenSealedClass.SplashScreen
    ) {

        composeAnimatedSlide<ScreenSealedClass.SplashScreen> {
            SplashScreen(navigateToHomeScreen = { navigator.onAction(NavigationActions.NavigateToHomeScreen) })
        }

        //here we can apply nested navigation also
        composeAnimatedSlide<ScreenSealedClass.HomeScreen> {
            val viewModel: HomeViewModel = hiltViewModel()
            HomeScreen(
                viewModel = viewModel,
                onEvent = viewModel::onEvent,
                navigateToDetailScreen = {
                    navigator.onAction(
                        NavigationActions.NavigateToDetailsScreen(it)
                    )
                }) {
                navigator.onAction(NavigationActions.NavigateToSearchScreen)
            }
        }

        composeAnimatedSlide<ScreenSealedClass.SearchScreen> {
            val viewModel: SearchViewModel = hiltViewModel()
            SearchScreen(
                viewModel = viewModel,
                onEvent = viewModel::onEvent,
                onBackClicked = { navigator.onAction(NavigationActions.NavigateBack) }) {
                navigator.onAction(NavigationActions.NavigateToDetailsScreen(it))
            }
        }

        composeAnimatedSlide<ScreenSealedClass.DetailsScreen> {
            val viewModel: DetailsViewModel = hiltViewModel()
            DetailsScreen(
                viewModel = viewModel,
                onEvent = viewModel::onEvent,
                onBackClicked = { navigator.onAction(NavigationActions.NavigateBack) })
        }

    }
}