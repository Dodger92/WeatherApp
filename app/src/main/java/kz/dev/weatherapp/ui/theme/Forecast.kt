package kz.dev.weatherapp.ui.theme

data class ForecastModel(
    var city: String = "",
    var weather: String = "",
    var wind: String = "",
    var humidity: String = "",
    var pressure_mb: String = "",
    var condition: String = "",
    var feelslike_c: String = "",
    var condition_icon: String = "",
    var forecastForWeek: List<ForecastForWeekItem> = listOf()
)

data class ForecastForWeekItem(
    val dayOfWeek: String= "",
    val maxTemp: String= "",
    val minTemp: String= "",
    val condition_icon: String= ""
)