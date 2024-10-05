package com.itssinghankit.stockex.domain.model.home

import android.health.connect.datatypes.units.Percentage
import android.health.connect.datatypes.units.Volume

data class GainersLoosersModel (
    val symbol:String,
    val price:String,
    val changeAmount:String,
    val changePercentage: String,
    val volume: String
)