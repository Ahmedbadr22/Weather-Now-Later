package com.ab.weather_now_later.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ab.core.utils.constants.AppConst.NAV_TRANSITION_DURATION
import com.ab.today_city_weather.TodayCityWeatherScreen
import com.ab.today_city_weather.TodayCityWeatherViewModel


@Composable
fun MainNavigationHost(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = TodayCityWeatherScreenRoute,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(NAV_TRANSITION_DURATION)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(NAV_TRANSITION_DURATION)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.End,
                animationSpec = tween(NAV_TRANSITION_DURATION)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.End,
                animationSpec = tween(NAV_TRANSITION_DURATION)
            )
        }
    ) {
        composable<TodayCityWeatherScreenRoute> {
            val viewModel: TodayCityWeatherViewModel = hiltViewModel()

            val cityName: String by viewModel.cityName.collectAsStateWithLifecycle()

            TodayCityWeatherScreen(
                cityName = cityName,
                onCityNameChange = viewModel::onCityNameChange,
                onGetWeatherForecast = viewModel::onClickGetWeatherForecast
            )
        }
    }
}