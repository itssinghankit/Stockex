package com.itssinghankit.stockex.data.mapper

import com.itssinghankit.stockex.data.remote.dto.details.DetailsResDto
import com.itssinghankit.stockex.domain.model.details.DetailsModel

fun DetailsResDto.toDetailsModel(): DetailsModel {
    return this.run {
        DetailsModel(
            marketCap = MarketCapitalization,
            peRatio = PERatio,
            dividendYield = DividendYield,
            eps = EPS,
            bookValue = BookValue,
            revenue = RevenueTTM,
            netIncome = GrossProfitTTM,
            assetType = AssetType,
            name = Name,
            description = Description,
            exchange = Exchange,
            currency = Currency,
            symbol = Symbol
        )
    }
}