package com.interview.weatherapp.service

import com.interview.weatherapp.model.CityInfo
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("weather?")
    fun weather(
        @Query("lon") longitude: Double,
        @Query("lat") latitude: Double,
        @Query("appid") appKey: String
    ): Single<CityInfo>
}