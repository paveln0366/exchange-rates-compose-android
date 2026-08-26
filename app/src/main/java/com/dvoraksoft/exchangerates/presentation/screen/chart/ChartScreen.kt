package com.dvoraksoft.exchangerates.presentation.screen.chart

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dvoraksoft.exchangerates.R
import com.dvoraksoft.exchangerates.ui.theme.ExchangeRatesTheme

@Preview(showBackground = true)
@Composable
fun ChartScreenPreview() {
    ExchangeRatesTheme {
        ChartScreen()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChartScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {}
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.chart_screen_app_bar_title).uppercase(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { onBackClick() }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    Surface(
                        modifier = Modifier.padding(end = 12.dp),
                        shape = RoundedCornerShape(4.dp),
                        border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.5f)),
                        color = Color.Transparent,
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                modifier = Modifier.size(16.dp),
                                painter = painterResource(id = R.drawable.ic_usa),
                                contentDescription = null,
                                tint = Color.Unspecified
                            )
                            Text(
                                text = stringResource(R.string.chart_screen_text_usd),
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                            Icon(
                                modifier = Modifier.size(16.dp),
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = null,
                                tint = Color.Gray
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        containerColor = Color.White
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            contentPadding = innerPadding,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.chart_screen_text_today),
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "2.5782",
                        fontSize = 20.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    color = Color.Cyan.copy(alpha = 0.1f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(R.string.chart_screen_text_trading),
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = "2.5807",
                            fontSize = 12.sp,
                            color = Color.Black
                        )
                        Text(
                            text = stringResource(R.string.chart_screen_text_change),
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = "0.0025",
                            fontSize = 12.sp,
                            color = Color.Green
                        )
                        Text(
                            text = stringResource(R.string.chart_screen_text_update),
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = "27.01 10:49",
                            fontSize = 12.sp,
                            color = Color.Black
                        )
                    }
                }
            }
            item {
                Chart()
            }
            item {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        modifier = modifier,
                        selected = true,
                        onClick = {},
                        label = {
                            Text("Неделя")
                        }
                    )
                    FilterChip(
                        modifier = modifier,
                        selected = false,
                        onClick = {},
                        label = {
                            Text("Месяц")
                        }
                    )
                    FilterChip(
                        modifier = modifier,
                        selected = false,
                        onClick = {},
                        label = {
                            Text("Квартал")
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun Chart() {

}