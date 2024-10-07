package com.itssinghankit.stockex.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.itssinghankit.stockex.R
import com.itssinghankit.stockex.domain.model.home.GainersLoosersModel
import kotlin.math.abs

@Composable
fun GainersAndLoosers(
    modifier: Modifier,
    topic: String,
    gainersLoosersList: List<GainersLoosersModel>,
    onCardClick:(String)->Unit
) {
    Column(modifier = modifier) {
        Text(
            text = topic,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.secondary,
        )
        LazyRow(
            Modifier.padding(top = 16.dp),
            contentPadding = PaddingValues(horizontal = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(gainersLoosersList.size) { index ->
                StockCard(
                    symbol = gainersLoosersList[index].symbol,
                    volume = gainersLoosersList[index].volume,
                    price = gainersLoosersList[index].price,
                    changeAmount = gainersLoosersList[index].changeAmount,
                    percentage = gainersLoosersList[index].changePercentage,
                    onCardClick = onCardClick
                )
            }
        }
    }
}

@Composable
fun StockCard(
    modifier: Modifier = Modifier,
    symbol: String,
    volume: String,
    price: String,
    changeAmount: String,
    percentage: String,
    onCardClick:(String)->Unit
) {
    Card(
        modifier = modifier.width(200.dp).clickable { onCardClick(symbol) },
        shape = MaterialTheme.shapes.extraSmall,
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        val changeTxt = if (changeAmount.toFloat() > 0) {
            "+$${String.format("%.2f",changeAmount.toFloat())} (+${percentage})"
        } else {
            "-$${abs(changeAmount.toFloat())} (${percentage})"
        }
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier.padding(0.dp), verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = stringResource(R.string.company_logo),
                    contentDescription = "avatar",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(MaterialTheme.shapes.extraSmall)
                        .border(1.dp, MaterialTheme.colorScheme.outline),
                    contentScale = ContentScale.Crop,
                    placeholder = MaterialTheme.colorScheme.outlineVariant.let { ColorPainter(it) }
                )
                Column {
                    Text(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        text = symbol,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.gilroy_semibold)),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        modifier = Modifier.padding(start = 12.dp, top = 2.dp, end = 12.dp),
                        text = "Vol: $volume",
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                }
            }
            Text(
                modifier = Modifier.padding(top = 24.dp),
                text = "${String.format("%.2f",price.toFloat())} USD",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                modifier = Modifier.padding(top = 6.dp),
                text = changeTxt,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.SemiBold,
                color = if (changeAmount.toDouble() > 0) MaterialTheme.colorScheme.onTertiaryContainer else MaterialTheme.colorScheme.error,
            )

        }
    }
}