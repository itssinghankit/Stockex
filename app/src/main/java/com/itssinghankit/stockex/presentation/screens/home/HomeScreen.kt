package com.itssinghankit.stockex.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Search
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.itssinghankit.stockex.R
import com.itssinghankit.stockex.presentation.components.ConnectionLostScreen
import com.itssinghankit.stockex.presentation.components.SnackBarHostComponent
import com.itssinghankit.stockex.presentation.ui.transparentblack
import com.itssinghankit.stockex.util.NetworkMonitor
import kotlinx.coroutines.launch


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    onEvent: (HomeEvents) -> Unit,
    onSearchClicked: () -> Unit
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
        topBar = {
                TopAppBar()
        },
        snackbarHost = {
            SnackBarHostComponent(hostState = snackBarHostState) {
                onEvent(HomeEvents.ResetErrorMessage)
            }
        }) { innerPadding ->


            HomeContent(
                modifier = modifier.padding(innerPadding),
                onSearchClicked = onSearchClicked
            )

        }
        if (!networkState.isAvailable()) ConnectionLostScreen()

    }
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    onSearchClicked: () -> Unit
) {

    Column(modifier = modifier) {
        SearchTextField(modifier = Modifier.padding(top = 8.dp), onSearchClicked = onSearchClicked)
    }

}

@Composable
fun SearchTextField(modifier: Modifier = Modifier, onSearchClicked: () -> Unit) {
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(
                color = MaterialTheme.colorScheme.outline,
                shape = MaterialTheme.shapes.small
            )
            .clickable { onSearchClicked() }
    ) {
        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Search,
                contentDescription = "Search icon",
                tint = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.size(18.dp)
            )
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = stringResource(R.string.search_stock),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.secondary,
            )
        }
    }
}


@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    name: String = stringResource(R.string.home_screen_name),
    avatar: String = stringResource(R.string.home_screen_avatar),
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(vertical = 24.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = avatar,
                contentDescription = "avatar",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                placeholder = MaterialTheme.colorScheme.outlineVariant.let { ColorPainter(it) }

            )
            Column {
                Text(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    text = "Good Morning,",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary,
                )
                Text(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    text = "Hello, $name",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.secondary,
                )
            }
        }

        Icon(
            modifier = Modifier
                .clickable { }
                .border(1.dp, MaterialTheme.colorScheme.outline)
                .padding(10.dp),
            imageVector = Icons.Filled.Notifications,
            contentDescription = ""
        )

    }

}


