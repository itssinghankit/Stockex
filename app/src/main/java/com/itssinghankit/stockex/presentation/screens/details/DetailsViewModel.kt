package com.itssinghankit.stockex.presentation.screens.details

import android.content.Context
import android.telecom.Call.Details
import android.widget.Toast
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.itssinghankit.stockex.presentation.navigation.ScreenSealedClass
import com.itssinghankit.stockex.util.NetworkMonitor
import com.itssinghankit.stockex.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlin.contracts.contract

data class DetailsStates(
    val isLoading:Boolean=false,
    val errorMessage:UiText?=null,
    val symbol:String?=null,
)

@HiltViewModel
class DetailsViewModel @Inject constructor(
    networkMonitor: NetworkMonitor,
    private val savedStateHandle: SavedStateHandle,
    @ApplicationContext private val context:Context
):ViewModel() {

    private val  _states = MutableStateFlow(DetailsStates())
    val states: StateFlow<DetailsStates> = _states

    val networkState =networkMonitor.networkState

    init {
        _states.update {
            it.copy(
                symbol = savedStateHandle.toRoute<ScreenSealedClass.DetailsScreen>().symbol
            )
        }
        getDetails()
    }


    fun onEvent(event:DetailsEvents){

        when(event){
            DetailsEvents.ResetErrorMessage->{
                _states.update {
                    it.copy(errorMessage=null)
                }
            }
        }
    }

    private fun getDetails() {
        Toast.makeText( context, states.value.symbol, Toast.LENGTH_SHORT).show()
    }


}