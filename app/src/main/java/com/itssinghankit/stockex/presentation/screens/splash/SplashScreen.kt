package com.itssinghankit.stockex.presentation.screens.splash

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SplashScreen(modifier: Modifier = Modifier,navigateToHomeScreen:()->Unit) {
    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        Text("Splash Screen", modifier = modifier.padding(innerPadding))
        navigateToHomeScreen()
    }
}