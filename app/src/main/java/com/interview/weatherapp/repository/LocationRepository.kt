package com.interview.weatherapp.repository

import com.interview.weatherapp.model.Coordinates

class LocationRepository {

    private val _coordinates = listOf(
        Coordinates("Stockholm", 59.334591, 18.063240),
        Coordinates("Gothenburg", 57.708870, 11.974560),
        Coordinates("Mountain View", 37.386051, -122.083855),
        Coordinates("London", 51.50735,  -0.12776),
        Coordinates("New York",  40.7166638, -74.0),
        Coordinates("Berlin", 52.520008, 13.404954)
    )

    val coordinates get() = _coordinates
}