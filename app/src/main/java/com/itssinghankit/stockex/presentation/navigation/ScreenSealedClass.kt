package com.itssinghankit.stockex.presentation.navigation

import kotlinx.serialization.Serializable


sealed class ScreenSealedClass {

    @Serializable
    data object SplashScreen : ScreenSealedClass()

    @Serializable
    data object HomeScreen:ScreenSealedClass()

    @Serializable
    data object SearchScreen:ScreenSealedClass()

    @Serializable
    data class DetailsScreen(val symbol:String):ScreenSealedClass()
}

