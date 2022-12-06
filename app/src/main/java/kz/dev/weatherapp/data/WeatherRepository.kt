package kz.dev.weatherapp.data

import kz.dev.weatherapp.data.response.one_city.OneCityResp
import javax.inject.Inject

class WeatherForecastRepository @Inject constructor(private val networkingService: ApiService) {

    suspend fun getCityForecast(key: String, cityName: String): OneCityResp =
        networkingService.getCityForecast(key = key, cityName = cityName)
}