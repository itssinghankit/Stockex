package com.itssinghankit.stockex.util

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.itssinghankit.stockex.presentation.navigation.ScreenSealedClass

inline fun <reified T : ScreenSealedClass> NavGraphBuilder.composeAnimatedSlide(
    duration: Int = 350,
    noinline content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable<T>(
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(duration)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(duration)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                animationSpec = tween(duration)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                animationSpec = tween(duration)
            )
        },
        content = content
    )
}