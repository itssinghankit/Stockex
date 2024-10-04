package com.itssinghankit.stockex.presentation.screens.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
            topBar = { HomeAppBar(onBackClicked = onBackClicked) },
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
    Column(modifier = modifier) {
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
    Card(modifier = modifier.fillMaxWidth(), shape = MaterialTheme.shapes.extraSmall,
        colors =CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.outline)) {
        Row(modifier.padding(4.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = companyIcon,
                contentDescription = "avatar",
                modifier = Modifier
                    .size(40.dp)
                    .clip(MaterialTheme.shapes.extraSmall),
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
    }
}

@Composable
fun HomeAppBar(
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