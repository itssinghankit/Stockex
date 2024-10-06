package com.itssinghankit.stockex.util

import android.os.Build
import androidx.annotation.RequiresApi
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
fun dateTimeToDateTime(dateTime: String): String{
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val outputFormatter = DateTimeFormatter.ofPattern("hh:mm a d MMM")

    val dateTimeResult = LocalDateTime.parse(dateTime, inputFormatter)
    return dateTimeResult.format(outputFormatter).uppercase()
}

@RequiresApi(Build.VERSION_CODES.O)
fun dateTimeToDate(dateTime: String): String{
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val outputFormatter = DateTimeFormatter.ofPattern("d MMM")

    val dateTimeResult = LocalDate.parse(dateTime, inputFormatter)
    return dateTimeResult.format(outputFormatter).uppercase()
}

@RequiresApi(Build.VERSION_CODES.O)
fun dateTimeToFullDate(dateTime: String): String{
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val outputFormatter = DateTimeFormatter.ofPattern("d MMM yyyy")

    val dateTimeResult = LocalDate.parse(dateTime, inputFormatter)
    return dateTimeResult.format(outputFormatter).uppercase()
}
