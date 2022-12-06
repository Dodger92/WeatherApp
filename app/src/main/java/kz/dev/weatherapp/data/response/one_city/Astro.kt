package kz.dev.weatherapp.data.response.one_city

data class Astro(
    var moon_illumination: String? = null,
    var moon_phase: String? = null,
    var moonrise: String? = null,
    var moonset: String? = null,
    var sunrise: String? = null,
    var sunset: String? = null
)