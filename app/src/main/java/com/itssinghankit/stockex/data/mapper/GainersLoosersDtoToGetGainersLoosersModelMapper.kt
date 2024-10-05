package com.itssinghankit.stockex.data.mapper

import com.itssinghankit.stockex.data.remote.dto.home.GainersLoosersDto
import com.itssinghankit.stockex.domain.model.home.GainersLoosersModel
import com.itssinghankit.stockex.domain.model.home.GetGainersLoosersModel

fun GainersLoosersDto.toGainersLoosersModel(): GetGainersLoosersModel {
    return GetGainersLoosersModel(
        gainers = this.top_gainers.map {
            GainersLoosersModel(
                symbol = it.ticker,
                price = it.price,
                changeAmount = it.change_amount,
                changePercentage = it.change_percentage,
                volume = it.volume
            )
        },
        loosersModel = this.top_losers.map {
            GainersLoosersModel(
                symbol = it.ticker,
                price = it.price,
                changeAmount = it.change_amount,
                changePercentage = it.change_percentage,
                volume = it.volume
            )
        }
    )
}