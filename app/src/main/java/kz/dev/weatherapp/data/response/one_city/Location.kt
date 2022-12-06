package kz.dev.weatherapp.data.response.one_city

data class Location(
    var country: String? = null,
    var lat: Double? = null,
    var localtime: String? = null,
    var localtime_epoch: Int? = null,
    var lon: Double? = null,
    var name: String? = null,
    var region: String? = null,
    var tz_id: String? = null
)