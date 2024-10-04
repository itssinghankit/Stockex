package com.itssinghankit.stockex.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.itssinghankit.stockex.presentation.ui.transparentblack

@Composable
fun ConnectionLostScreen() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(enabled = false) {}
            .background(transparentblack),
        contentAlignment = Alignment.BottomCenter
    ) {
        Text(
            text = "No Internet Connection",
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .navigationBarsPadding()
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.outline)
                .padding(vertical = 6.dp),
            textAlign = TextAlign.Center
        )
    }


}