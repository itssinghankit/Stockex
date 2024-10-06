package com.itssinghankit.stockex.data.mapper

import com.itssinghankit.stockex.data.remote.dto.details.week.WeekGraphDto
import com.itssinghankit.stockex.domain.model.details.ChartModel

fun WeekGraphDto.toChartModelListDto():List<ChartModel>{
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