package kz.dev.weatherapp.data.response.one_city

data class Forecast(
    var forecastday: List<Forecastday> = listOf()
)