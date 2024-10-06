package com.itssinghankit.stockex.data.mapper

import com.itssinghankit.stockex.data.remote.dto.details.DetailsPricePercentDto
import com.itssinghankit.stockex.domain.model.details.DetailsPricePercentModel

fun DetailsPricePercentDto.toDetailPricePercentModel():DetailsPricePercentModel{
    return this.globalQuote.run {
        DetailsPricePercentModel(
            price=price,
            change=change,
            changePercent=changePercent
        )
    }
}