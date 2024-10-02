package com.itssinghankit.stockex.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.itssinghankit.stockex.presentation.navigation.Navigator
import com.itssinghankit.stockex.presentation.navigation.RootNavGraph
import com.itssinghankit.stockex.presentation.ui.StockexTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StockexTheme {
                val rootNavController = rememberNavController()
                val navigator = Navigator(rootNavController)
                RootNavGraph(rootNavController, navigator)
            }
        }
    }
}

