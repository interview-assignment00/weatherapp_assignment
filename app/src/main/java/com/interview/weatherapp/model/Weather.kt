package com.interview.weatherapp.model

data class Weather(
    val id: Long,
    val main: String,
    val description: String,
    val icon: String
)