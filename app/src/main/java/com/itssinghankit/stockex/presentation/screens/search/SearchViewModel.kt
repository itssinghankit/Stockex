package com.itssinghankit.stockex.presentation.screens.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharesphere.util.ApiResult
import com.example.sharesphere.util.DataError
import com.itssinghankit.stockex.R
import com.itssinghankit.stockex.domain.model.search.SearchModel
import com.itssinghankit.stockex.domain.use_case.search.SearchUseCase
import com.itssinghankit.stockex.util.NetworkMonitor
import com.itssinghankit.stockex.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class SearchStates(
    val isLoading: Boolean = false,
    val errorMessage: UiText? = null,
    val searchResult: List<SearchModel>? = null,
    val recentSearchList: MutableList<SearchModel> = mutableListOf()
)

@HiltViewModel
class SearchViewModel @Inject constructor(
    networkMonitor: NetworkMonitor,
    private val searchUseCase: SearchUseCase
) : ViewModel() {

    private val _states = MutableStateFlow(SearchStates())
    val states: StateFlow<SearchStates> = _states

    var searchQuery by mutableStateOf("")

    val networkState = networkMonitor.networkState

    fun onEvent(event: SearchEvents) {
        when (event) {
            SearchEvents.ResetErrorMessage -> {
                _states.update {
                    it.copy(errorMessage = null)
                }
            }

            is SearchEvents.OnSearchQueryChanged -> {
                searchQuery = event.value
                if (searchQuery.isEmpty()) {
                    _states.update {
                        it.copy(searchResult = null)
                    }
                }
            }

            SearchEvents.OnClearClicked -> {
                searchQuery = ""
                _states.update {
                    it.copy(searchResult = null)
                }
            }

            SearchEvents.OnSearchClicked -> {

                if (searchQuery.isNotEmpty() && searchQuery.isNotBlank()) {
                    search()
                }
            }

            is SearchEvents.OnRecentSearchItemClicked -> {
                searchQuery = states.value.recentSearchList[event.index].name
                search()
            }

            is SearchEvents.OnSearchItemClicked -> {

                //add to recent search list
                if(states.value.recentSearchList.contains(states.value.searchResult?.get(event.index))){
                    states.value.recentSearchList.remove(states.value.searchResult?.get(event.index))
                }
                states.value.recentSearchList.add(0,states.value.searchResult?.get(event.index)!!)

            }
        }
    }

    private fun search() {

        viewModelScope.launch(Dispatchers.IO) {
            _states.update {
                it.copy(isLoading = true)
            }

            searchUseCase(searchQuery).collect { result ->
                when (result) {
                    is ApiResult.Error -> {

                        when (result.error) {
                            DataError.Network.INTERNAL_SERVER_ERROR -> {
                                withContext(Dispatchers.Main) {
                                    _states.update {
                                        it.copy(
                                            errorMessage = UiText.StringResource(R.string.errorInternalServerError),
                                            isLoading = false
                                        )
                                    }
                                }
                            }

                            else -> {
                                withContext(Dispatchers.Main) {
                                    _states.update {
                                        it.copy(
                                            errorMessage = UiText.StringResource(R.string.errorCheckInternet),
                                            isLoading = false
                                        )
                                    }
                                }
                            }
                        }
                    }

                    is ApiResult.Success -> {
                        withContext(Dispatchers.Main) {
                            _states.update {
                                it.copy(
                                    isLoading = false,
                                    searchResult = result.data
                                )
                            }
                        }
                    }
                }
            }
        }

//        viewModelScope.launch {
//
//            _states.update {
//                it.copy(
//                    isLoading = false,
//                    searchResult = listOf(
//                        "a",
//                        "b",
//                        "c",
//                        "d",
//                        "e",
//                        "f",
//                        "g",
//                        "h",
//                        "i",
//                        "j",
//                        "k",
//                        "l",
//                        "m",
//                        "n",
//                        "o",
//                        "p",
//                        "q",
//                        "r",
//                        "s",
//                        "t",
//                        "u",
//                        "v",
//                        "w",
//                        "x",
//                        "y",
//                        "z"
//                    ),
//                    recentSearchList = it.recentSearchList.apply {
//                        add(0,searchQuery)
//                    }
//                )
//            }
//
//        }




    }

}