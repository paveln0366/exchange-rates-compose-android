package com.dvoraksoft.exchangerates.presentation.screen.rate

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.dvoraksoft.exchangerates.R
import com.dvoraksoft.exchangerates.domain.entity.Rate
import com.dvoraksoft.exchangerates.presentation.ui.theme.Accent
import com.dvoraksoft.exchangerates.presentation.ui.theme.ExchangeRatesTheme
import com.dvoraksoft.exchangerates.presentation.ui.theme.Green
import com.dvoraksoft.exchangerates.presentation.ui.theme.Red
import java.util.Locale

@Preview(showBackground = true)
@Composable
fun RateScreenPreview() {
    ExchangeRatesTheme {
        RateScreen()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RateScreen(
    modifier: Modifier = Modifier,
    viewModel: RateViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onBackClick()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                val date = (uiState as? RatesUiState.Success)?.date ?: ""
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.byn_screen_title, date),
                    fontSize = 16.sp,
                    color = Color.DarkGray,
                    textAlign = TextAlign.Center

                )
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    modifier = Modifier.wrapContentWidth(),
                    onClick = {
                        viewModel.refreshRates()
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Accent,
                        contentColor = Color.White
                    ),
                ) {
                    Text(
                        text = stringResource(R.string.update_button_title).uppercase(),
                        fontSize = 20.sp,
                    )
                }
            }

            when (val state = uiState) {
                RatesUiState.Loading -> {
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        CircularProgressIndicator(color = Accent)
                    }
                }

                is RatesUiState.Success -> {
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(2.dp)
                                .background(Accent)
                        )
                    }
                    items(state.rates) { rate ->
                        RateRowItem(rate)
                    }
                }

                is RatesUiState.Error -> {
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = state.message, color = Color.Red)
                    }
                }
            }
        }
    }
}

@Composable
fun RateRowItem(rate: Rate) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
            .border(0.5.dp, Color.LightGray),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .weight(2f)
                .fillMaxHeight()
                .padding(start = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = rate.flagUrl,
                contentDescription = rate.abbreviation,
                modifier = Modifier
                    .width(24.dp)
                    .height(16.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = rate.name,
                fontSize = 12.sp,
                color = Color.DarkGray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        VerticalDivider(
            modifier = Modifier
                .fillMaxHeight()
                .width(0.5.dp),
            color = Color.LightGray
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(end = 8.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            Text(
                text = String.format(Locale.US, "%.4f", rate.rate),
                fontSize = 12.sp,
                color = Color.DarkGray
            )
        }

        VerticalDivider(
            modifier = Modifier
                .fillMaxHeight()
                .width(0.5.dp),
            color = Color.LightGray
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(end = 8.dp),
            contentAlignment = Alignment.CenterEnd
        ) {
            val isPositive = rate.delta > 0
            val color = if (isPositive) Green else Red
            val sign = if (isPositive) "+" else ""
            val delta = String.format(Locale.US, "%s%.4f", sign, rate.delta)

            Text(
                text = delta,
                fontSize = 12.sp,
                color = color
            )
        }
    }
}