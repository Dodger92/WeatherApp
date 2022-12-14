package kz.dev.weatherapp.data.response.one_city

data class Hour(
    var chance_of_rain: Int? = 0,
    var chance_of_snow: Int? = 0,
    var cloud: Int? = 0,
    var condition: Condition? = Condition(),
    var dewpoint_c: Double? = 0.0,
    var dewpoint_f: Double? = 0.0,
    var feelslike_c: Double? = 0.0,
    var feelslike_f: Double? = 0.0,
    var gust_kph: Double? = 0.0,
    var gust_mph: Double? = 0.0,
    var heatindex_c: Double? = 0.0,
    var heatindex_f: Double? = 0.0,
    var humidity: Int? = 0,
    var is_day: Int? = 0,
    var precip_in: Double? = 0.0,
    var precip_mm: Double? = 0.0,
    var pressure_in: Double? = 0.0,
    var pressure_mb: Double? = 0.0,
    var temp_c: Double? = 0.0,
    var temp_f: Double? = 0.0,
    var time: String? = "",
    var time_epoch: Int? = 0,
    var uv: Double? = 0.0,
    var vis_km: Double? = 0.0,
    var vis_miles: Double? = 0.0,
    var will_it_rain: Int? = 0,
    var will_it_snow: Int? = 0,
    var wind_degree: Int? = 0,
    var wind_dir: String? = "",
    var wind_kph: Double? = 0.0,
    var wind_mph: Double? = 0.0,
    var windchill_c: Double? = 0.0,
    var windchill_f: Double? = 0.0
)