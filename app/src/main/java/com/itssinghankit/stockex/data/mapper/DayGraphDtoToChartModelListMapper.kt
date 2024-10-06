package com.itssinghankit.stockex.data.mapper

import com.itssinghankit.stockex.data.remote.dto.details.day.DayGraphDto
import com.itssinghankit.stockex.domain.model.details.ChartModel

fun DayGraphDto.toChartModelListDto():List<ChartModel>{
    return this.timeSeries.map {
        val average = it.value.run{
            (high.toFloat()+low.toFloat())/2.0f
        }
        ChartModel(
            label = it.key,
            value = average
        )
    }
}