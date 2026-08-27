package com.dvoraksoft.exchangerates.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.dvoraksoft.exchangerates.presentation.navigation.NavGraph
import com.dvoraksoft.exchangerates.presentation.ui.theme.ExchangeRatesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExchangeRatesTheme {
                NavGraph()
            }
        }
    }
}