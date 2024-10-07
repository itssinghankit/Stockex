package com.itssinghankit.stockex.util

import android.os.Build
import androidx.annotation.RequiresApi
import com.itssinghankit.stockex.presentation.screens.details.ChartType
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun dateTimeToTime(dateTime: String): String {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val outputFormatter = DateTimeFormatter.ofPattern("hh:mm a")

    val dateTimeResult = LocalDateTime.parse(dateTime, inputFormatter)
    return dateTimeResult.format(outputFormatter).uppercase()
}

@RequiresApi(Build.VERSION_CODES.O)
fun dateTimeToDateTime(dateTime: String): String {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val outputFormatter = DateTimeFormatter.ofPattern("hh:mm a d MMM")

    val dateTimeResult = LocalDateTime.parse(dateTime, inputFormatter)
    return dateTimeResult.format(outputFormatter).uppercase()
}

@RequiresApi(Build.VERSION_CODES.O)
fun dateToDate(dateTime: String): String {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val outputFormatter = DateTimeFormatter.ofPattern("d MMM")

    val dateTimeResult = LocalDate.parse(dateTime, inputFormatter)
    return dateTimeResult.format(outputFormatter).uppercase()
}

@RequiresApi(Build.VERSION_CODES.O)
fun dateToFullDate(dateTime: String): String {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val outputFormatter = DateTimeFormatter.ofPattern("d MMM yyyy")

    val dateTimeResult = LocalDate.parse(dateTime, inputFormatter)
    return dateTimeResult.format(outputFormatter).uppercase()
}

@RequiresApi(Build.VERSION_CODES.O)
fun filterChartItems(
    dateTime: String,
    outputType: ChartType,
    inputType: DateTimePatterns
): Boolean {
    val currentDate = LocalDate.now()
    val date = LocalDate.parse(dateTime, DateTimeFormatter.ofPattern(inputType.pattern))
    return when (outputType) {
        ChartType.WEEK -> date.isAfter(currentDate.minusWeeks(1)) && !date.isAfter(currentDate)
        ChartType.ONE_YEAR -> date.isAfter(currentDate.minusYears(1)) && !date.isAfter(currentDate)
        ChartType.FIVE_YEAR -> date.isAfter(currentDate.minusYears(5)) && !date.isAfter(currentDate)
        else -> true
    }
}

enum class DateTimePatterns(val pattern: String) {
    INCOMING_DATE_TIME("yyyy-MM-dd HH:mm:ss"),
    INCOMING_DATE("yyyy-MM-dd"),
    DATE("d MMM"),
    FULL_DATE("d MMM yyyy"),
    TIME("hh:mm a"),
    DATE_TIME("hh:mm a d MMM")
}
