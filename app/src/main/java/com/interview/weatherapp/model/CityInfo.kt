package com.interview.weatherapp.model

data class CityInfo(
    val name: String,
    val weather: List<Weather>,
    val main: Temperature
)