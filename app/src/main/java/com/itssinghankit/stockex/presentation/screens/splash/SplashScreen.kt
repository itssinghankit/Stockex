package com.itssinghankit.stockex.presentation.screens.splash

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.rememberTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(modifier: Modifier = Modifier, navigateToHomeScreen: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(3000)
        navigateToHomeScreen()
    }
    val infiniteTransition = rememberInfiniteTransition()
    val heightBar1 by infiniteTransition.animateValue(
        label = "heightBar1",
        initialValue = 4.dp,
        targetValue = 32.dp,
        typeConverter = Dp.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val heightBar2 by infiniteTransition.animateValue(
        label = "heightBar2",
        initialValue = 2.dp,
        targetValue = 16.dp,
        typeConverter = Dp.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(750, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val heightBar3 by infiniteTransition.animateValue(
        label = "heightBar3",
        initialValue = 0.dp,
        targetValue = 24.dp,
        typeConverter = Dp.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(850, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Row {
                Text(
                    text = "Stock",
                    style = MaterialTheme.typography.displayLarge,
                    fontWeight = FontWeight.ExtraBold,
                )
                Text(
                    text = "ex",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.tertiary
                )
                Row(
                    modifier = Modifier
                        .height(32.dp)
                        .padding(start = 6.dp)
                        .offset(y = 4.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    VerticalDivider(
                        modifier = Modifier
                            .height(heightBar1)
                            .clip(RoundedCornerShape(2.dp))
                        , thickness = 4.dp, color = MaterialTheme.colorScheme.primary
                    )
                    VerticalDivider(
                        modifier = Modifier
                            .padding(start = 2.dp)
                            .height(heightBar2)
                            .clip(RoundedCornerShape(2.dp)),
                        thickness = 4.dp,
                        color = MaterialTheme.colorScheme.primary
                    )
                    VerticalDivider(
                        modifier = Modifier
                            .padding(start = 2.dp)
                            .height(heightBar3)
                            .clip(RoundedCornerShape(2.dp)),
                        thickness = 4.dp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }


            }
        }

    }
}
