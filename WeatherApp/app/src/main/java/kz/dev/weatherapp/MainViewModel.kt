package kz.dev.weatherapp

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kz.dev.weatherapp.data.CoroutineDispatcherProvider
import kz.dev.weatherapp.data.WeatherForecastRepository
import kz.dev.weatherapp.ui.theme.ForecastModel
import kz.dev.weatherapp.ui.theme.ForecastForWeekItem
import retrofit2.HttpException
import java.util.*
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class MainViewModel @Inject constructor(
    private val repository: WeatherForecastRepository,
    @ApplicationContext private val applicationContext: Context,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider,
) : ViewModel() {

    val _uiState = MutableStateFlow<WeatherForecastUiState>(WeatherForecastUiState.Empty)
    val uiState: StateFlow<WeatherForecastUiState> = _uiState

    init {
        getCityForecast("Almaty")
    }

    fun getCityForecast(city: String) {
        _uiState.value = WeatherForecastUiState.Loading
        viewModelScope.launch(coroutineDispatcherProvider.IO()) {
            try {
                val response = repository.getCityForecast(key = API_KEY, cityName = city)
                var dateCounter = -1
                _uiState.value = WeatherForecastUiState.Loaded(
                    ForecastModel(
                        city = "${response.location.name}",
                        weather = "${response.current.temp_c}°",
                        wind = "${response.current.wind_kph} м/c",
                        humidity = "${response.current.humidity} %",
                        condition = "${response.current.condition?.text}",
                        pressure_mb = "${response.current.pressure_mb} мбар",
                        condition_icon = "http:${response.current.condition?.icon}",
                        feelslike_c = "По ощущению ${response.current.feelslike_c}°",
                        forecastForWeek = response.forecast.forecastday.map {
                            dateCounter++
                            ForecastForWeekItem(
                                dayOfWeek = Calendar.getInstance()
                                    .also { cal -> cal.add(Calendar.DATE, dateCounter) }
                                    .getDisplayName(
                                        Calendar.DAY_OF_WEEK,
                                        Calendar.LONG,
                                        Locale("ru")
                                    ).orEmpty(),
                                maxTemp = "${it.day?.maxtemp_c}°c",
                                minTemp = "${it.day?.mintemp_c}°c",
                                condition_icon = "http:${it.day?.condition?.icon}"
                            )
                        }
                    )
                )
            } catch (ex: Exception) {
                if (ex is HttpException && ex.code() == 429) {
                    onLimitReached()
                } else {
                    onError()
                }
            }
        }
    }

    private fun onLimitReached() {
        _uiState.value = WeatherForecastUiState.Error(
            applicationContext.getString(R.string.query_limit_reached)
        )
    }

    private fun onError() {
        _uiState.value = WeatherForecastUiState.Error(
            applicationContext.getString(R.string.something_went_wrong)
        )
    }

    sealed class WeatherForecastUiState {
        object Empty : WeatherForecastUiState()
        object Loading : WeatherForecastUiState()
        class Loaded(val data: ForecastModel) : WeatherForecastUiState()
        class Error(val message: String) : WeatherForecastUiState()
    }

    companion object {
        const val API_KEY = "7c3123b7eabe49c2ab2145848220212"
    }
}