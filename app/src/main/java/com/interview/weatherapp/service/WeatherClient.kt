package com.interview.weatherapp.service

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object WeatherClient {

    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    fun retrofit(): Retrofit {
        val httpClient = OkHttpClient
            .Builder()
            .build()

        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(httpClient)
            .build()
    }
}