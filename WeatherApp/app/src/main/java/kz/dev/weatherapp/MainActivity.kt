package kz.dev.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dagger.hilt.android.AndroidEntryPoint
import kz.dev.weatherapp.ui.theme.ForecastModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurrentWeatherHeader()
        }
    }
}

@Composable
fun CurrentWeatherHeader(mainViewModel: MainViewModel = viewModel()) {
    val heightInPx = with(LocalDensity.current) {
        LocalConfiguration.current
            .screenHeightDp.dp.toPx()
    }
    Column(
        Modifier
            .background(
                Brush.verticalGradient(
                    listOf(colorResource(R.color.blue_light), colorResource(R.color.blue_dark)),
                    0f,
                    heightInPx * 1.1f
                )
            )
            .fillMaxHeight()
    ) {
        when (val state = mainViewModel.uiState.collectAsState().value) {
            is MainViewModel.WeatherForecastUiState.Empty -> Text(
                text = stringResource(R.string.no_data_available),
                modifier = Modifier.padding(16.dp)
            )
            is MainViewModel.WeatherForecastUiState.Loading ->
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                }
            is MainViewModel.WeatherForecastUiState.Error -> ErrorDialog(state.message)
            is MainViewModel.WeatherForecastUiState.Loaded -> ForecastLoadedScreen(state.data)
        }
    }

}

@Composable
fun ForecastLoadedScreen(data: ForecastModel, mainViewModel: MainViewModel = viewModel()) {
    var showCityDialog by remember { mutableStateOf(false) }
    if (showCityDialog) {
        ShowCityDialog(
            onDismiss = {
                showCityDialog = !showCityDialog
            }
        )
    }
    Row(
        Modifier
            .padding(top = 20.dp, start = 40.dp, end = 10.dp, bottom = 10.dp)
            .clickable {
                showCityDialog = true
            }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.location),
            contentDescription = stringResource(R.string.location_ic),
            modifier = Modifier.size(27.dp),
            tint = White
        )
        Text(
            text = data.city,
            modifier = Modifier.padding(start = 10.dp),
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp),
            textAlign = TextAlign.Center,
            color = White
        )
        Icon(
            painter = painterResource(id = R.drawable.arrow),
            contentDescription = stringResource(R.string.location_ic),
            modifier = Modifier.padding(start = 10.dp, top = 10.dp),
            tint = White
        )
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(data.condition_icon)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.ic_placeholder),
            contentDescription = stringResource(R.string.condition),
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(120.dp)
        )
        Text(
            text = data.weather,
            modifier = Modifier.padding(start = 0.dp),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 54.sp,
                color = White,
                textAlign = TextAlign.Center
            )
        )
        Text(
            text = data.condition,
            modifier = Modifier.padding(start = 0.dp),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = White,
                textAlign = TextAlign.Center
            )
        )
        Text(
            text = data.feelslike_c,
            modifier = Modifier.padding(start = 0.dp),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = White,
                textAlign = TextAlign.Center
            )
        )
    }

    Row(
        Modifier
            .padding(top = 30.dp, start = 20.dp, end = 20.dp)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(20.dp))
            .height(40.dp)
            .background(color = colorResource(id = R.color.bg)),
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.pressure),
            contentDescription = stringResource(R.string.pressure),
            tint = White,
            modifier = Modifier
                .padding(start = 10.dp, top = 10.dp)
                .size(24.dp)
        )
        Column(modifier = Modifier.align(Alignment.CenterVertically)) {
            Text(
                text = data.pressure_mb,
                modifier = Modifier.padding(start = 5.dp),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = White,
                    textAlign = TextAlign.Center
                )
            )
        }
        Icon(
            painter = painterResource(id = R.drawable.humidity),
            contentDescription = stringResource(R.string.humidity),
            tint = White,
            modifier = Modifier
                .padding(start = 10.dp, top = 10.dp)
                .size(24.dp)
        )
        Column(modifier = Modifier.align(Alignment.CenterVertically)) {
            Text(
                text = data.humidity,
                modifier = Modifier.padding(start = 5.dp),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = White,
                    textAlign = TextAlign.Center
                )
            )
        }

        Icon(
            painter = painterResource(id = R.drawable.wind),
            contentDescription = stringResource(R.string.wind),
            tint = White,
            modifier = Modifier
                .padding(start = 10.dp, top = 10.dp)
                .size(24.dp)
        )
        Column(modifier = Modifier.align(Alignment.CenterVertically)) {
            Text(
                text = data.wind,
                modifier = Modifier.padding(start = 5.dp),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = White
                )
            )
        }
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 20.dp)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(color = colorResource(id = R.color.bg)),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(items = data.forecastForWeek, itemContent = { card ->
            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                val (text1, image, row) = createRefs()

                val chainRef =
                    createHorizontalChain(text1, image, row, chainStyle = ChainStyle.SpreadInside)

                constrain(chainRef) {
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                }
                Text(
                    text = card.dayOfWeek,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    ),
                    color = White,
                    modifier = Modifier
                        .padding(bottom = 4.dp, top = 10.dp)
                        .constrainAs(text1) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                            width = Dimension.fillToConstraints
                        }
                )
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(card.condition_icon)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.ic_placeholder),
                    contentDescription = stringResource(R.string.condition_day),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(50.dp)
                        .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
                        .constrainAs(image) {
                            start.linkTo(text1.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            width = Dimension.fillToConstraints
                        }
                )

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(row) {
                        start.linkTo(image.end)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                    }, horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = card.maxTemp,
                        style = TextStyle(
                            fontSize = 18.sp,
                            textAlign = TextAlign.End
                        ),
                        color = White,
                        modifier = Modifier.padding(start = 5.dp, end = 5.dp)
                    )

                    Text(
                        text = card.minTemp,
                        style = TextStyle(
                            fontSize = 18.sp,
                            textAlign = TextAlign.End
                        ),
                        color = White,
                        modifier = Modifier.padding(start = 5.dp, end = 5.dp)
                    )
                }
            }
            Divider()
        })
    }
}

@Composable
fun ErrorDialog(message: String) {
    val openDialog = remember { mutableStateOf(true) }
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = stringResource(R.string.something_went_wrong))
            },
            text = {
                Text(message)
            },
            confirmButton = {
                openDialog.value = false
            }
        )
    }
}

@Composable
private fun ShowCityDialog(
    mainViewModel: MainViewModel = viewModel(),
    onDismiss: () -> Unit,
) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            elevation = 8.dp,
            shape = RoundedCornerShape(12.dp),
            backgroundColor = colorResource(id = R.color.bg)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = stringResource(R.string.select_city),
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(8.dp),
                    color = White
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column {
                        Text(
                            text = stringResource(R.string.almaty),
                            fontSize = 18.sp,
                            color = White,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable {
                                    mainViewModel.getCityForecast("Almaty")
                                })
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = stringResource(R.string.astana),
                            fontSize = 18.sp,
                            color = White,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable {
                                    mainViewModel.getCityForecast("Astana")
                                })
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}