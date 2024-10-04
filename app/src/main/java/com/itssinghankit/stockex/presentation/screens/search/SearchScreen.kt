package com.itssinghankit.stockex.presentation.screens.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.itssinghankit.stockex.domain.model.search.SearchModel
import com.itssinghankit.stockex.presentation.components.ConnectionLostScreen
import com.itssinghankit.stockex.presentation.components.Loading
import com.itssinghankit.stockex.presentation.components.SearchListItem
import com.itssinghankit.stockex.presentation.components.SnackBarHostComponent
import com.itssinghankit.stockex.util.NetworkMonitor
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel,
    onEvent: (SearchEvents) -> Unit,
    onBackClicked: () -> Unit,
    onSearchItemClicked: (String) -> Unit
) {
    val scope = rememberCoroutineScope()
    val states by viewModel.states.collectAsStateWithLifecycle()
    val snackBarHostState: SnackbarHostState = remember {
        SnackbarHostState()
    }
    val networkState by
    viewModel.networkState.collectAsStateWithLifecycle(initialValue = NetworkMonitor.NetworkState.Lost)
    val searchQuery = viewModel.searchQuery
    val keyboard = LocalSoftwareKeyboardController.current

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
                SearchTopBar(
                    onBackClicked = onBackClicked,
                    searchQuery = searchQuery,
                    onSearchValueChanged = { onEvent(SearchEvents.OnSearchQueryChanged(it)) },
                    onClearClicked = { onEvent(SearchEvents.OnClearClicked) },
                    onSearchClicked = {
                        keyboard?.hide()
                        onEvent(SearchEvents.OnSearchClicked)
                    }
                )
            },
            snackbarHost = {
                SnackBarHostComponent(hostState = snackBarHostState) {
                    onEvent(SearchEvents.ResetErrorMessage)
                }
            }) { innerPadding ->

            Box(modifier = modifier.padding(innerPadding)) {
                SearchContent(
                    isLoading = states.isLoading,
                    searchResult = states.searchResult,
                    onSearchItemClicked = {
                        keyboard?.hide()
                        onEvent(SearchEvents.OnSearchItemClicked(it))
                        viewModel.states.value.searchResult?.get(it)?.let { item ->
                            onSearchItemClicked(
                                item.symbol
                            )
                        }
                    },
                    recentSearchList = states.recentSearchList,
                    onRecentSearchItemClicked = {
                        keyboard?.hide()
                        onEvent(SearchEvents.OnRecentSearchItemClicked(it))
                    }
                )

            }

        }
        if (!networkState.isAvailable()) ConnectionLostScreen()
    }
}

@Composable
fun SearchContent(
    isLoading: Boolean,
    searchResult: List<SearchModel>?,
    onSearchItemClicked: (Int) -> Unit,
    recentSearchList: MutableList<SearchModel>,
    onRecentSearchItemClicked: (Int) -> Unit
) {
    if (isLoading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Loading()
        }
    } else if (searchResult != null) {
        if (searchResult.isEmpty()) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 32.dp),
                text = "No Result found",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.secondary,
            )
        } else {
            SearchList(
                searchResult = searchResult,
                onSearchItemClicked = onSearchItemClicked
            )

        }
    } else {
        RecentSearchList(
            recentSearchList = recentSearchList,
            onRecentSearchItemClicked = onRecentSearchItemClicked
        )
    }
}

@Composable
fun SearchList(
    modifier: Modifier = Modifier,
    searchResult: List<SearchModel>,
    onSearchItemClicked: (Int) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(searchResult.size) { index ->
            searchResult[index].run {
                SearchListItem(
                    onSearchItemClicked = { onSearchItemClicked(index) },
                    stockName = name,
                    stockSymbol = symbol,
                    stockType = type
                )
            }
        }
    }
}

@Composable
fun RecentSearchList(
    recentSearchList: MutableList<SearchModel>,
    onRecentSearchItemClicked: (Int) -> Unit
) {
    LazyColumn {
        items(recentSearchList.size) { index ->
            recentSearchList[index].run {
                SearchListItem(
                    icon = Icons.Filled.History,
                    onSearchItemClicked = { onRecentSearchItemClicked(index) },
                    stockName = name,
                    stockSymbol = symbol,
                    stockType = type
                )
            }
        }
    }
}

@Composable
fun SearchTopBar(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit,
    searchQuery: String,
    onSearchValueChanged: (String) -> Unit,
    onClearClicked: () -> Unit,
    onSearchClicked: () -> Unit
) {

    var isTextFieldFocused by rememberSaveable { mutableStateOf(false) }

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

            BasicTextField(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp)
                    .onFocusChanged { focusState ->
                        isTextFieldFocused = focusState.isFocused
                    },
                value = if (!isTextFieldFocused && searchQuery.isEmpty()) "Search stock..." else searchQuery,
                onValueChange = onSearchValueChanged,
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W500,
                    color = if (isTextFieldFocused) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { onSearchClicked() }),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.primary)
            )
            if (searchQuery.isNotEmpty() && searchQuery.isNotBlank()) {
                Icon(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .clip(CircleShape)
                        .clickable { onClearClicked() }
                        .padding(8.dp),
                    imageVector = Icons.Filled.Close,
                    contentDescription = "back button"
                )
            }

        }

        HorizontalDivider(
            color = MaterialTheme.colorScheme.outlineVariant,
            thickness = 1.dp
        )
    }
}


