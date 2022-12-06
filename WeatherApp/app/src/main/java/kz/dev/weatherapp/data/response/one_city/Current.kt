package kz.dev.weatherapp.data.response.one_city

data class Current(
    var cloud: Int? = null,
    var condition: Condition? = null,
    var feelslike_c: Double? = null,
    var feelslike_f: Double? = null,
    var gust_kph: Double? = null,
    var gust_mph: Double? = null,
    var humidity: Int? = null,
    var is_day: Int? = null,
    var last_updated: String? = null,
    var last_updated_epoch: Int? = null,
    var precip_in: Double? = null,
    var precip_mm: Double? = null,
    var pressure_in: Double? = null,
    var pressure_mb: Double? = null,
    var temp_c: Double? = null,
    var temp_f: Double? = null,
    var uv: Double? = null,
    var vis_km: Double? = null,
    var vis_miles: Double? = null,
    var wind_degree: Int? = null,
    var wind_dir: String? = null,
    var wind_kph: Double? = null,
    var wind_mph: Double? = null
)