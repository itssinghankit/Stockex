package com.itssinghankit.stockex.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SearchListItem(
    icon: ImageVector = Icons.Filled.Search,
    onSearchItemClicked: () -> Unit,
    stockName: String,
    stockSymbol: String,
    stockType: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSearchItemClicked() }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "search icon",
            tint = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .padding(start = 16.dp)
                .background(color = MaterialTheme.colorScheme.outline, shape = CircleShape)
                .padding(12.dp)
                .size(16.dp)
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = stockName,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(start = 16.dp)
            )
            Text(
                text = stockSymbol,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.W600,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(start = 16.dp, top = 6.dp)
            )
        }
        Text(
            text = stockType,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.W600,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(end = 16.dp)
        )
    }
}