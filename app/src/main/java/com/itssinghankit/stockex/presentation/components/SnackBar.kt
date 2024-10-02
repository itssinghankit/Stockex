package com.itssinghankit.stockex.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun SnackBarHostComponent(
    modifier: Modifier = Modifier,
    hostState: SnackbarHostState,
    action: () -> Unit
) {

    SnackbarHost(hostState = hostState, snackbar = {
        Snackbar(
            modifier = modifier
                .wrapContentSize(align = Alignment.BottomCenter)
                .padding(bottom = 32.dp),
            action = { action() },
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = it.visuals.message,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
            )
        }
    })

}