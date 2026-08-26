package com.dvoraksoft.exchangerates.presentation.screen.basket

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dvoraksoft.exchangerates.R
import com.dvoraksoft.exchangerates.ui.theme.Accent
import com.dvoraksoft.exchangerates.ui.theme.ExchangeRatesTheme

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
    onBackClick: () -> Unit = {},
    onDateSelect: () -> Unit = {},
    onDisplayClick: () -> Unit = {}
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    Icon(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .clickable { onBackClick() },
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = ""
                    )
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(all = 16.dp),
            contentPadding = innerPadding,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.basket_screen_title),
                    fontSize = 16.sp,
                    color = Color.DarkGray,
                    textAlign = TextAlign.Center

                )
            }
            item {
                Box(
                    modifier = modifier
                        .fillMaxWidth()
                        .border(width = 1.dp, color = Color.LightGray)
                        .padding(all = 12.dp)
                ) {
                    Row {
                        val state = rememberTextFieldState()
                        TextField(
                            state = state,
                            modifier = Modifier.weight(2f),
                            readOnly = true,
                            lineLimits = TextFieldLineLimits.SingleLine,
                            placeholder = {
                                Text(stringResource(R.string.date_placeholder))
                            },
                            trailingIcon = {
                                IconButton(
                                    onClick = { onDateSelect() }
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.DateRange, "",
                                        tint = Color.LightGray
                                    )
                                }
                            },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                errorContainerColor = Color.Transparent
                            )
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Button(
                            modifier = Modifier.weight(1f),
                            onClick = { onDisplayClick() },
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
            }
            item {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(R.string.basket_screen_description),
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    lineHeight = 14.sp

                )
            }
            items(10) { item ->
            }
        }
    }
}