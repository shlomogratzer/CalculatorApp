package com.example.calculatorapp.simpelcalculator

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CalculatorApp(viewModel: CalculatorViewModel = viewModel()) {
    val display = viewModel.display
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    LaunchedEffect(display) {
        scrollState.scrollTo(scrollState.maxValue)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
                .height(32.dp)
        )
        Column(modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .background(Color.Blue)
        ){
            ExitButton(
                label = "Exit",
                modifier = Modifier.padding(16.dp),
                onClick = { (context as Activity).finish() }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.surface) ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = display,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .horizontalScroll(scrollState)
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .padding(16.dp),
                maxLines = 1,
                softWrap = false,
                textAlign = TextAlign.End
            )

            val buttons = listOf(
                listOf("←", "C"),
                listOf("7", "8", "9", "/"),
                listOf("4", "5", "6", "*"),
                listOf("1", "2", "3", "-"),
                listOf("=", "0", ".", "+")
            )

            buttons.forEach { row ->
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    row.forEach { label ->
                        CalculatorButton(
                            label = label,
                            modifier = Modifier.weight(1f),
                            onClick = { viewModel.onButtonClick(label) }
                        )
                    }
                }
            }
        }
    }
}