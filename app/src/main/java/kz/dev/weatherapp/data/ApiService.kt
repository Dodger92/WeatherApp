package kz.dev.weatherapp.data

import kz.dev.weatherapp.data.response.one_city.OneCityResp
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v1/forecast.json")
    suspend fun getCityForecast(
        @Query("key") key: String,
        @Query("q") cityName: String,
        @Query("days") days: String = "7",
        @Query("aqi") aqi: String = "no",
        @Query("alerts") alerts: String = "no",
        @Query("lang") lang: String = "ru",
    ): OneCityResp
}