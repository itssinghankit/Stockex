package com.itssinghankit.stockex.presentation.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharesphere.util.ApiResult
import com.example.sharesphere.util.DataError
import com.itssinghankit.stockex.R
import com.itssinghankit.stockex.domain.model.home.GetGainersLoosersModel
import com.itssinghankit.stockex.domain.use_case.home.GetGainersLoosersUseCase
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

data class HomeStates(
    val isLoading: Boolean = false,
    val errorMessage: UiText? = null,
    val gainersLoosers: GetGainersLoosersModel? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    networkMonitor: NetworkMonitor,
    private val getGainersLoosersUseCase: GetGainersLoosersUseCase
) : ViewModel() {

    private val _states = MutableStateFlow(HomeStates())
    val states: StateFlow<HomeStates> = _states

    val networkState = networkMonitor.networkState

    init {
        getGainersLoosers()
    }

    fun onEvent(event: HomeEvents) {
        when (event) {

            HomeEvents.ResetErrorMessage -> {
                _states.update {
                    it.copy(errorMessage = null)
                }
            }
        }
    }

    private fun getGainersLoosers() {

        viewModelScope.launch(Dispatchers.IO) {
            _states.update {
                it.copy(isLoading = true)
            }

            getGainersLoosersUseCase().collect { result ->
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
                            DataError.Network.API_LIMIT_EXCEEDED -> {
                                withContext(Dispatchers.Main) {
                                    _states.update {
                                        it.copy(
                                            errorMessage = UiText.StringResource(R.string.errorApiLimit),
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
                                    gainersLoosers = result.data
                                )
                            }
                        }
                    }
                }
            }
        }

    }

}