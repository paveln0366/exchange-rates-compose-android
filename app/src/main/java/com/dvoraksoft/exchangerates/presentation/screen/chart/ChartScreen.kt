package com.dvoraksoft.exchangerates.presentation.screen.chart

import android.graphics.Paint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.dvoraksoft.exchangerates.R
import com.dvoraksoft.exchangerates.domain.model.Dynamic
import com.dvoraksoft.exchangerates.domain.model.PeriodType
import com.dvoraksoft.exchangerates.presentation.ui.theme.Accent
import com.dvoraksoft.exchangerates.presentation.ui.theme.Blue
import com.dvoraksoft.exchangerates.presentation.ui.theme.ExchangeRatesTheme
import com.dvoraksoft.exchangerates.presentation.ui.theme.Green
import com.dvoraksoft.exchangerates.presentation.ui.theme.Red
import kotlinx.datetime.number
import java.util.Locale

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
    viewModel: ChartViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

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
                    IconButton(onClick = { onBackClick() }) {
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
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            when (val state = uiState) {
                is ChartUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Accent)
                    }
                }

                is ChartUiState.Success -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.chart_screen_text_today),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = String.format(Locale.ROOT, "%.4f", state.rate),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Label(
                        rate = state.rate,
                        change = state.change,
                        updated = state.updated
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = stringResource(R.string.chart_screen_text_dynamics),
                        fontSize = 12.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        Chart(dynamics = state.dynamics)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        PeriodChip(
                            text = stringResource(R.string.chart_screen_week),
                            isSelected = state.selectedPeriod == PeriodType.WEEK,
                            onClick = { viewModel.onPeriodSelected(PeriodType.WEEK) }
                        )
                        PeriodChip(
                            text = stringResource(R.string.chart_screen_month),
                            isSelected = state.selectedPeriod == PeriodType.MONTH,
                            onClick = { viewModel.onPeriodSelected(PeriodType.MONTH) }
                        )
                        PeriodChip(
                            text = stringResource(R.string.chart_screen_quarter),
                            isSelected = state.selectedPeriod == PeriodType.QUARTER,
                            onClick = { viewModel.onPeriodSelected(PeriodType.QUARTER) }
                        )
                    }
                }

                is ChartUiState.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = state.message, color = Red)
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                onClick = { viewModel.onPeriodSelected(PeriodType.WEEK) },
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Accent,
                                    contentColor = Color.White
                                ),
                            ) {
                                Text(
                                    text = stringResource(R.string.update).uppercase(),
                                    fontSize = 20.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Label(
    rate: Double,
    change: Double,
    updated: String?
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.Cyan.copy(alpha = 0.1f)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.chart_screen_text_trading),
                color = Color.Gray,
                fontSize = 12.sp
            )
            Text(
                text = String.format(Locale.ROOT, "%.4f", rate),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(R.string.chart_screen_text_change),
                color = Color.Gray,
                fontSize = 12.sp
            )

            val prefix = if (change > 0) "+" else ""
            val changeColor = when {
                change > 0 -> Green
                change < 0 -> Red
                else -> Color.Black
            }
            val change = "$prefix${String.format(Locale.ROOT, "%.4f", change)}"
            Text(
                text = change,
                color = changeColor,
                fontSize = 12.sp
            )
            Text(
                text = stringResource(R.string.chart_screen_text_update),
                color = Color.Gray,
                fontSize = 12.sp
            )

            if (!updated.isNullOrEmpty()) {
                Text(
                    text = updated,
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun PeriodChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val textColor = if (isSelected) Color.White else Color.DarkGray
    val borderColor = if (isSelected) Accent else Color.DarkGray
    val backgroundColor = if (isSelected) Accent else Color.Transparent

    Box(
        modifier = Modifier
            .border(1.dp, borderColor, RoundedCornerShape(8.dp))
            .background(backgroundColor, RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(horizontal = 8.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 16.sp
        )
    }
}

@Composable
fun Chart(
    dynamics: List<Dynamic>,
    modifier: Modifier = Modifier
) {
    if (dynamics.isEmpty()) return

    val minRate = dynamics.minOf { it.rate }
    val maxRate = dynamics.maxOf { it.rate }
    val rateRange = if (maxRate == minRate) 1.0 else maxRate - minRate

    val lineColor = Blue
    val gridColor = Color.LightGray

    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height
        val paddingBottom = 40.dp.toPx()
        val paddingTop = 20.dp.toPx()
        val chartHeight = height - paddingBottom - paddingTop
        val steps = 4

        for (i in 0..steps) {
            val y = paddingTop + chartHeight * (1 - i.toFloat() / steps)
            drawLine(
                color = gridColor,
                start = Offset(0f, y),
                end = Offset(width, y),
                strokeWidth = 1.dp.toPx()
            )

            val rateVal = minRate + (rateRange * (i.toFloat() / steps))
            drawContext.canvas.nativeCanvas.drawText(
                String.format(Locale.ROOT, "%.3f", rateVal),
                10f,
                y - 8f,
                Paint().apply {
                    color = android.graphics.Color.GRAY
                    textSize = 24f
                }
            )
        }

        val xStep = width / (dynamics.size - 1).coerceAtLeast(1)
        val coordinates = dynamics.mapIndexed { index, point ->
            val x = index * xStep
            val normalizedY = (point.rate - minRate) / rateRange
            val y = paddingTop + chartHeight * (1 - normalizedY.toFloat())
            Offset(x, y)
        }

        val path = Path().apply {
            coordinates.forEachIndexed { index, offset ->
                if (index == 0) moveTo(offset.x, offset.y)
                else lineTo(offset.x, offset.y)
            }
        }

        drawPath(
            path = path,
            color = lineColor,
            style = Stroke(width = 2.dp.toPx())
        )

        coordinates.forEach { offset ->
            drawCircle(
                color = lineColor,
                radius = 3.dp.toPx(),
                center = offset
            )
        }

        val labelStep = (dynamics.size / 5).coerceAtLeast(1)
        dynamics.forEachIndexed { index, point ->
            if (index % labelStep == 0 || index == dynamics.lastIndex) {
                val x = index * xStep
                val day = point.date.day.toString().padStart(2, '0')
                val month = point.date.month.number.toString().padStart(2, '0')
                val date = "${day}.${month}"
                drawContext.canvas.nativeCanvas.drawText(
                    date,
                    x - 20f,
                    height - 10f,
                    Paint().apply {
                        color = android.graphics.Color.GRAY
                        textSize = 24f
                    }
                )
            }
        }
    }
}