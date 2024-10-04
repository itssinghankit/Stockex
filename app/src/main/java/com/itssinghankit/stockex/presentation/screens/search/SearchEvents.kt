package com.itssinghankit.stockex.presentation.screens.search

import android.app.appsearch.AppSearchManager.SearchContext
import com.itssinghankit.stockex.presentation.screens.home.HomeEvents

sealed class SearchEvents {
    data object ResetErrorMessage: SearchEvents()
    data class OnSearchQueryChanged(val value:String): SearchEvents()
    data object OnClearClicked: SearchEvents()
    data object OnSearchClicked:SearchEvents()
    data class OnSearchItemClicked(val index:Int):SearchEvents()
    data class OnRecentSearchItemClicked(val index:Int):SearchEvents()

}