package com.itssinghankit.stockex.presentation.screens.home

sealed class HomeEvents {
    data object ResetErrorMessage:HomeEvents()
    data object OnSearchActiveClosed:HomeEvents()
    data class onSearchQueryChanged(val value:String):HomeEvents()

}