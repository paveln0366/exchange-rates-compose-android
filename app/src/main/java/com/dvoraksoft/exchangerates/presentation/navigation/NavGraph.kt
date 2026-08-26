package com.dvoraksoft.exchangerates.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dvoraksoft.exchangerates.presentation.screen.basket.BasketScreen
import com.dvoraksoft.exchangerates.presentation.screen.byn.BynScreen
import com.dvoraksoft.exchangerates.presentation.screen.chart.ChartScreen
import com.dvoraksoft.exchangerates.presentation.screen.main.MainScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ) {
        composable(Screen.Main.route) {
            MainScreen(
                onNavigateToBynScreen = {
                    navController.navigate(Screen.Byn.route)
                },
                onNavigateToBasketScreen = {
                    navController.navigate(Screen.Basket.route)
                },
                onNavigateToChartScreen = {
                    navController.navigate(Screen.Chart.route)
                }
            )
        }

        composable(Screen.Byn.route) {
            BynScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Basket.route) {
            BasketScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Chart.route) {
            ChartScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}

sealed class Screen(val route: String) {

    data object Main : Screen("main")
    data object Byn : Screen("byn")
    data object Basket : Screen("basket")
    data object Chart : Screen("chart")
}