package com.dvoraksoft.exchangerates

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.dvoraksoft.exchangerates.presentation.screen.main.MainScreen
import com.dvoraksoft.exchangerates.ui.theme.ExchangeRatesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExchangeRatesTheme {
                MainScreen()
            }
        }
    }
}