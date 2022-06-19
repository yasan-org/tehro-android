package dev.yasan.metro.tehran.presentation.compose.screen.line

import android.location.Location
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.sharp.SwapVert
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.birjuvachhani.locus.Locus
import com.birjuvachhani.locus.LocusResult
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dev.yasan.kit.compose.foundation.grid
import dev.yasan.kit.core.Resource
import dev.yasan.metro.tehran.R
import dev.yasan.metro.tehran.presentation.util.entity.Action
import dev.yasan.metro.tehran.presentation.util.entity.LaunchSource
import dev.yasan.metro.tehran.domain.entity.line.Line
import dev.yasan.metro.tehran.domain.entity.station.Station
import dev.yasan.metro.tehran.presentation.compose.common.teh.TehError
import dev.yasan.metro.tehran.presentation.compose.common.teh.TehFooter
import dev.yasan.metro.tehran.presentation.compose.common.teh.TehProgress
import dev.yasan.metro.tehran.presentation.compose.common.teh.TehScreen
import dev.yasan.metro.tehran.presentation.compose.screen.line.modules.StationItem
import dev.yasan.metro.tehran.presentation.navigation.NavGraph
import dev.yasan.metro.tehran.presentation.navigation.NavRoutes
import dev.yasan.metro.tehran.presentation.theme.TehroIcons

/**
 * This screen shows the detailed data for a single [Line] which mainly includes the list of [Station]s inside it.
 *
 * @see NavGraph
 * @see NavRoutes.routeLine
 * @see NavRoutes.routeLineBase
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LineScreen(
    lineViewModel: LineViewModel,
    navController: NavController,
    lineId: Int,
) {

    val context = LocalContext.current

    val title = lineViewModel.title.observeAsState(initial = "")
    val lineColor = lineViewModel.lineColor.observeAsState(initial = Color.DarkGray)
    val stations = lineViewModel.stations.observeAsState(initial = Resource.Initial())

    rememberSystemUiController().setStatusBarColor(color = lineColor.value)

    val orderAscending = rememberSaveable { mutableStateOf(true) }

    LaunchedEffect(key1 = stations.value) {
        if (stations.value is Resource.Initial) {
            lineViewModel.loadStations(lineId = lineId)
        }
    }

    val buttonAngle by animateFloatAsState(if (orderAscending.value) 180f else 0f)

    val locusResult: State<LocusResult?> = Locus.startLocationUpdates(context).observeAsState()
    val userLocation = locusResult.value?.location

    TehScreen(
        title = title.value,
        color = lineColor.value,
        actions = listOf(
            Action(
                iconModifier = Modifier.rotate(buttonAngle),
                icon = TehroIcons.SwapVert,
                text = "",
                onClick = {
                    orderAscending.value = !orderAscending.value
                }
            )
        )
    ) {

        when (stations.value) {
            is Resource.Error -> {
                item {
                    TehError {
                        lineViewModel.loadStations(lineId = lineId)
                    }
                }
            }
            is Resource.Success -> {

                val list = stations.value.data ?: ArrayList()

                val stationCount = list.size

                item {
                    Spacer(modifier = Modifier.requiredHeight(grid()))
                }

                items(
                    items = list.apply {
                        if (orderAscending.value) {
                            sortBy { it.positionInLine }
                        } else {
                            sortByDescending { it.positionInLine }
                        }
                    },
                    key = { it.id }
                ) { station ->
                    val distance = if (userLocation == null || !station.hasLocation) null else {
                        val stationLocation = Location("")
                        stationLocation.latitude = station.locationLatitude ?: 0.0
                        stationLocation.longitude = station.locationLongitude ?: 0.0
                        val distance = stationLocation.distanceTo(userLocation)
                        distance.toInt()
                    }
                    StationItem(
                        modifier = Modifier.animateItemPlacement(),
                        station = station,
                        navController = navController,
                        launchSource = LaunchSource.LINE,
                        distance = distance
                    )
                }

                item {
                    TehFooter(text = stringResource(R.string.n_stations, stationCount))
                }
            }
            else -> {
                item {
                    TehProgress()
                }
            }
        }

    }

    DisposableEffect(key1 = lineViewModel) {
        onDispose {
            Locus.stopLocationUpdates()
        }
    }

}
