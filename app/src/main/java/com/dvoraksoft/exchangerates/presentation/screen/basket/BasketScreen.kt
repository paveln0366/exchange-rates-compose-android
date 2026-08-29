package com.dvoraksoft.exchangerates.presentation.screen.basket

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.dvoraksoft.exchangerates.R
import com.dvoraksoft.exchangerates.domain.entity.Basket
import com.dvoraksoft.exchangerates.domain.entity.BasketItem
import com.dvoraksoft.exchangerates.presentation.ui.theme.Accent
import com.dvoraksoft.exchangerates.presentation.ui.theme.ExchangeRatesTheme
import com.dvoraksoft.exchangerates.presentation.ui.theme.Green
import com.dvoraksoft.exchangerates.presentation.ui.theme.Red
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import java.util.Locale
import kotlin.time.Clock
import kotlin.time.Instant


@Preview(showBackground = true)
@Composable
fun BasketScreenPreview() {
    ExchangeRatesTheme {
        BasketScreen()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasketScreen(
    modifier: Modifier = Modifier,
    viewModel: BasketViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    var showDatePicker by remember { mutableStateOf(false) }

    Scaffold(
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (val state = uiState) {
                is BasketUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Accent)
                    }
                }

                is BasketUiState.Success -> {
                    val date = "${state.date.day}.${state.date.month.number}.${state.date.year}"

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.basket_screen_title, date),
                        fontSize = 16.sp,
                        color = Color.DarkGray,
                        textAlign = TextAlign.Center

                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(width = 1.dp, color = Color.LightGray.copy(alpha = 0.5f))
                            .padding(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TextField(
                                modifier = Modifier.weight(1f),
                                value = date,
                                onValueChange = {},
                                readOnly = true,
                                label = {
                                    Text(stringResource(R.string.date_placeholder))
                                },
                                trailingIcon = {
                                    Icon(
                                        modifier = Modifier.clickable {
                                            showDatePicker = true
                                        },
                                        imageVector = Icons.Outlined.DateRange,
                                        tint = Color.Gray,
                                        contentDescription = null,
                                    )
                                },
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    disabledContainerColor = Color.Transparent,
                                    errorContainerColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Gray,
                                    unfocusedIndicatorColor = Color.Gray,
                                    focusedLabelColor = Color.Gray,
                                    unfocusedLabelColor = Color.Gray
                                )
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            Button(
                                onClick = { viewModel.refreshBasket(state.date) },
                                shape = RectangleShape,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Accent,
                                    contentColor = Color.White
                                ),
                            ) {
                                Text(
                                    text = stringResource(R.string.display_button_title),
                                    fontSize = 14.sp,
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(R.string.basket_screen_description),
                        fontSize = 12.sp,
                        color = Color.DarkGray,
                        lineHeight = 14.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    BasketTable(state.basket, state.date.year - 1)

                    val label = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.DarkGray)) {
                            append("Символом")
                        }
                        withStyle(style = SpanStyle(color = Green)) {
                            append(" ▲")
                        }
                        withStyle(style = SpanStyle(color = Color.DarkGray)) {
                            append(" обозначается укрепление белорусского рубля, символом")
                        }
                        withStyle(style = SpanStyle(color = Red)) {
                            append(" ▼")
                        }
                        withStyle(style = SpanStyle(color = Color.DarkGray)) {
                            append(" – ослабление белорусского рубля.")
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = label,
                        fontSize = 12.sp,
                        lineHeight = 14.sp
                    )
                }

                is BasketUiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = state.message, color = Color.Red)
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(onClick = {
                                val today = Clock.System.now()
                                    .toLocalDateTime(TimeZone.currentSystemDefault()).date
                                viewModel.onDateSelected(today)
                            }) {
                                Text("Повторить")
                            }
                        }
                    }
                }
            }
        }
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState()

        DatePickerDialog(
            onDismissRequest = {
                showDatePicker = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val date = Instant
                                .fromEpochMilliseconds(millis)
                                .toLocalDateTime(TimeZone.UTC)
                                .date
                            viewModel.onDateSelected(date)
                        }
                        showDatePicker = false
                    }
                ) {
                    Text(stringResource(R.string.ok))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDatePicker = false
                    }
                ) {
                    Text(stringResource(R.string.cancel))
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@Composable
private fun BasketTable(
    basket: Basket,
    prevYear: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 0.5.dp,
                color = Color.LightGray.copy(alpha = 0.5f)
            )
    ) {
        BasketTabRow(prevYear)
        BasketRow(basket.basket, isBold = true)
        BasketRow(basket.rub)
        BasketRow(basket.usd)
        BasketRow(basket.cny)
    }
}

@Composable
private fun BasketTabRow(prevYear: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Accent)
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TableCell(stringResource(R.string.tab_1), isHeader = true)
        TableCell(stringResource(R.string.tab_2), isHeader = true)
        TableCell(stringResource(R.string.tab_3, prevYear), isHeader = true)
        TableCell(stringResource(R.string.tab_4), isHeader = true)
    }
}

@Composable
private fun BasketRow(item: BasketItem, isBold: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .border(
                width = 0.5.dp,
                color = Color.LightGray.copy(alpha = 0.5f)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TableCell(item.name, isBold = isBold)
        VerticalDivider(
            Modifier
                .fillMaxHeight()
                .width(0.5.dp), color = Color.LightGray
        )
        TableCell(String.format(Locale.ROOT, "%.4f", item.rate), isBold = isBold, alignEnd = true)
        VerticalDivider(
            Modifier
                .fillMaxHeight()
                .width(0.5.dp), color = Color.LightGray
        )
        TableCellWithDelta(item.changePrevYearPercent)
        VerticalDivider(
            Modifier
                .fillMaxHeight()
                .width(0.5.dp), color = Color.LightGray
        )
        TableCellWithDelta(item.changePrevDayPercent)
    }
}

@Composable
private fun RowScope.TableCell(
    text: String,
    isHeader: Boolean = false,
    isBold: Boolean = false,
    alignEnd: Boolean = false
) {
    Text(
        text = text,
        modifier = Modifier
            .weight(1f)
            .padding(horizontal = 4.dp),
        color = if (isHeader) Color.White else Color.DarkGray,
        fontSize = if (isHeader) 10.sp else 12.sp,
        fontWeight = if (isHeader || isBold) FontWeight.Bold else FontWeight.Normal,
        textAlign = if (alignEnd) TextAlign.End else TextAlign.Center,
        lineHeight = 12.sp
    )
}

@Composable
private fun RowScope.TableCellWithDelta(
    delta: Double?
) {
    Box(
        modifier = Modifier
            .weight(1f)
            .padding(horizontal = 4.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        if (delta != null && delta != 0.0) {
            val isPositive = delta > 0
            val color = if (isPositive) Green else Red
            val arrow = if (isPositive) stringResource(R.string.positive)
            else stringResource(R.string.negative)
            val text = String.format(Locale.ROOT, "%.2f %s", delta, arrow)

            Text(
                text = text,
                color = color,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        } else {
            Text(
                text = "0.00",
                color = Color.DarkGray,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}