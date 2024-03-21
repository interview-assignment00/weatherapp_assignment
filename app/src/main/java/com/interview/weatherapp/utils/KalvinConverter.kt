package com.interview.weatherapp.utils

import kotlin.math.roundToInt

object KalvinConverter {

    fun toCelsiusString(kalvin: Double): String {
        val celsius = kalvin - 273.15
        return "${celsius.roundToInt()}°C"
    }
}