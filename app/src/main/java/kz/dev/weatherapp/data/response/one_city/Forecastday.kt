package kz.dev.weatherapp.data.response.one_city

data class Forecastday(
    var astro: Astro? = Astro(),
    var date: String? = "",
    var date_epoch: Int? = 0,
    var day: Day? = Day(),
    var hour: List<Hour>? = listOf()
)