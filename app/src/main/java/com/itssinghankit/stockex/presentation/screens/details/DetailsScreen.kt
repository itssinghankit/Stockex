package com.itssinghankit.stockex.presentation.screens.details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.itssinghankit.stockex.R
import com.itssinghankit.stockex.presentation.components.ConnectionLostScreen
import com.itssinghankit.stockex.presentation.components.SnackBarHostComponent
import com.itssinghankit.stockex.util.NetworkMonitor
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLine
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.compose.common.vicoTheme
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.cartesian.layer.LineCartesianLayer
import kotlinx.coroutines.launch

@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel,
    onEvent: (DetailsEvents) -> Unit,
    onBackClicked: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val states by viewModel.states.collectAsStateWithLifecycle()
    val snackBarHostState: SnackbarHostState = remember {
        SnackbarHostState()
    }
    val networkState by
    viewModel.networkState.collectAsStateWithLifecycle(initialValue = NetworkMonitor.NetworkState.Lost)

    //Showing snackBar for Errors
    states.errorMessage?.let { errorMessage ->
        val snackBarText = errorMessage.asString()
        scope.launch {
            snackBarHostState.showSnackbar(snackBarText)
        }
    }

    Box {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { DetailsAppBar(onBackClicked = onBackClicked) },
            snackbarHost = {
                SnackBarHostComponent(hostState = snackBarHostState) {
                    onEvent(DetailsEvents.ResetErrorMessage)
                }
            }) { innerPadding ->

            DetailsContent(modifier = Modifier.padding(innerPadding))

        }
        if (!networkState.isAvailable()) ConnectionLostScreen()
    }
}

@Composable
fun DetailsContent(modifier: Modifier) {
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        GraphCard(modifier = Modifier.padding(16.dp))
    }
}

@Composable
fun GraphCard(
    modifier: Modifier, companyIcon: String = stringResource(R.string.company_logo),
    symbol: String = "IBM",
    assetType: String = "Common Stock",
    exchange: String = "NYSE"
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.extraSmall,
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),

        ) {
        Column {
            Row(modifier.padding(0.dp), verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = companyIcon,
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
                        text = "$symbol, $assetType",
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.gilroy_semibold)),
                        color = MaterialTheme.colorScheme.primary,
                    )
                    Text(
                        modifier = Modifier.padding(start = 12.dp, top = 2.dp),
                        text = exchange,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                }
            }

            StockPriceDetails(modifier.padding(horizontal = 4.dp))
            LineGraph(modifier = Modifier.padding(top = 16.dp))
            GraphFilters()

        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GraphFilters() {

    var selectedFilterIndex by rememberSaveable { mutableIntStateOf(1) }
    val filterData = listOf(
        FilterData(1, "1D" ),
        FilterData(2, "1W" ),
        FilterData(3, "1M" ),
        FilterData(4, "1Y" ),
        FilterData(5, "5Y" ),
        FilterData(6, "ALL")
    )

    FlowRow(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        filterData.forEach { filter ->
            FilterChip(
                label = {
                    Text(
                        text = filter.label,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold
                    )
                },
                selected = filter.id == selectedFilterIndex,
                onClick = {
                    selectedFilterIndex = filter.id
                },
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun StockPriceDetails(modifier: Modifier) {
    Column(modifier = modifier) {
        Text(
            text = "$1576.29",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
        )
        Text(
            modifier = Modifier.padding(top = 6.dp),
            text = "+$12.29 (+0.78%)",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF6aa68b),
        )
    }
}

@Composable
fun LineGraph(modifier: Modifier = Modifier) {
    val modelProducer = remember { CartesianChartModelProducer() }
    LaunchedEffect(Unit) {
        modelProducer.runTransaction {
            lineSeries { series(4, 12, 8, 16, 4, 6, 3, 5, 2, 7, 2, 7, 8, 3, 8, 7, 1, 8) }
        }
    }
    CartesianChartHost(

        rememberCartesianChart(
            rememberLineCartesianLayer(
                lineProvider =
                LineCartesianLayer.LineProvider.series(
                    vicoTheme.lineCartesianLayerColors.map { color ->
                        LineCartesianLayer.rememberLine(
                            LineCartesianLayer.LineFill.single(
                                fill(
                                    Color.Green
                                )
                            )
                        )
                    }
                ),
            ),
            bottomAxis = HorizontalAxis.rememberBottom(guideline = null),
        ),

        modelProducer,
        modifier = modifier.fillMaxWidth()

    )
}

@Composable
fun DetailsAppBar(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit,
    companyName: String = "Company"
) {
    Column(modifier = modifier.statusBarsPadding()) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = Modifier
                    .padding(12.dp)
                    .clip(CircleShape)
                    .clickable { onBackClicked() }
                    .padding(8.dp),
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "back button"
            )

            Text(
                text = companyName,
                style = MaterialTheme.typography.titleLarge,
            )

        }

        HorizontalDivider(
            color = MaterialTheme.colorScheme.outlineVariant,
            thickness = 1.dp
        )
    }
}

data class FilterData(
    val id: Int,
    val label: String,
)