package com.itssinghankit.stockex.data.mapper

import com.itssinghankit.stockex.data.remote.dto.search.SearchResDto
import com.itssinghankit.stockex.domain.model.search.SearchModel

fun SearchResDto.toSearchModelList(): List<SearchModel> {
    return this.bestMatches.map {
        it.run {
            SearchModel(
                name = name,
                symbol = symbol,
                type = type
            )
        }
    }
}