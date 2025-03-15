package com.ab.weather_now_later

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.ab.core.ui.theme.WeatherNowLaterTheme
import com.ab.weather_now_later.navigation.MainNavigationHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navHostController = rememberNavController()
            WeatherNowLaterTheme {
                MainNavigationHost(navHostController)
            }
        }
    }
}
