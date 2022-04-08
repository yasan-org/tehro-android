package dev.yasan.metro.tehran.presentation.navigation

import dev.yasan.metro.tehran.model.misc.LaunchSource
import dev.yasan.metro.tehran.model.tehro.Line
import dev.yasan.metro.tehran.model.tehro.Station
import dev.yasan.metro.tehran.presentation.composable.screen.home.HomeScreen
import dev.yasan.metro.tehran.presentation.composable.screen.line.LineScreen
import dev.yasan.metro.tehran.presentation.composable.screen.map.MapScreen
import dev.yasan.metro.tehran.presentation.composable.screen.search.SearchScreen
import dev.yasan.metro.tehran.presentation.composable.screen.station.StationScreen

/**
 * Handles all the navigation routes used on [NavGraph].
 */
object NavRoutes {

    // Home

    private const val ROUTE_HOME = "home"

    /**
     * @return Navigation route for [HomeScreen].
     */
    fun routeHome() = ROUTE_HOME

    // Line

    private const val ROUTE_LINE = "line"
    const val EXTRA_LINE_ID = "lineId"

    /**
     * @return Navigation route for [LineScreen] with navigation arguments.
     */
    fun routeLine(line: Line) = "$ROUTE_LINE/${line.id}"

    /**
     * @return Navigation route for [LineScreen].
     */
    fun routeLineBase() = "$ROUTE_LINE/{$EXTRA_LINE_ID}"

    // Station

    private const val ROUTE_STATION = "station"
    const val EXTRA_STATION_ID = "stationId"
    const val EXTRA_LAUNCH_SOURCE = "launchSource"

    /**
     * @return Navigation route for [StationScreen] with navigation arguments.
     */
    fun routeStation(station: Station, launchSource: LaunchSource) = "$ROUTE_STATION/${station.id}/${launchSource.ordinal}"

    /**
     * @return Navigation route for [StationScreen].
     */
    fun routeStationBase() = "$ROUTE_STATION/{$EXTRA_STATION_ID}/{$EXTRA_LAUNCH_SOURCE}"

    // Search

    private const val ROUTE_SEARCH = "search"

    /**
     * @return Navigation route for [SearchScreen].
     */
    fun routeSearch() = ROUTE_SEARCH

    // Map

    private const val ROUTE_MAP = "map"

    /**
     * @return Navigation route for [MapScreen].
     */
    fun routeMap() = ROUTE_MAP

    // About

    private const val ROUTE_ABOUT = "about"

    /**
     * @return Navigation route for [StationScreen].
     */
    fun routeAbout() = ROUTE_ABOUT

}
