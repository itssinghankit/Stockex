package com.itssinghankit.stockex.presentation.navigation

import kotlinx.serialization.Serializable


sealed class ScreenSealedClass {

    @Serializable
    data object SplashScreen : ScreenSealedClass()

    @Serializable
    data object HomeScreen:ScreenSealedClass()
}

