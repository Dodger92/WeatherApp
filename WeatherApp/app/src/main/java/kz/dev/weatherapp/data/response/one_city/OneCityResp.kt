package kz.dev.weatherapp.data.response.one_city

data class OneCityResp(
    var current: Current = Current(),
    var forecast: Forecast = Forecast(),
    var location: Location = Location()
)