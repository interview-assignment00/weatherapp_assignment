package com.interview.weatherapp.repository

import com.interview.weatherapp.model.Coordinates
import com.interview.weatherapp.service.WeatherService
import com.interview.weatherapp.model.CityInfo
import com.interview.weatherapp.service.WeatherClient
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class CityInfoRepository {

    fun cityInfo(coordinates: Coordinates, appKey: String): Single<CityInfo> {
        return WeatherClient
            .retrofit()
            .create(WeatherService::class.java)
            .weather(
                latitude = coordinates.latitude,
                longitude = coordinates.longitude,
                appKey = appKey
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}