package com.itssinghankit.stockex.presentation.screens.home

import androidx.lifecycle.ViewModel
import com.itssinghankit.stockex.util.NetworkMonitor
import com.itssinghankit.stockex.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class HomeStates(
    val isLoading: Boolean = false,
    val errorMessage: UiText? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    networkMonitor: NetworkMonitor
) : ViewModel() {

    private val _states = MutableStateFlow(HomeStates())
    val states: StateFlow<HomeStates> = _states


    val networkState = networkMonitor.networkState

    fun onEvent(event: HomeEvents) {
        when (event) {

            HomeEvents.ResetErrorMessage -> {
                _states.update {
                    it.copy(errorMessage = null)
                }
            }
        }
    }

}