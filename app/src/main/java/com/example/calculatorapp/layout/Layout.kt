package com.example.calculatorapp.layout

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.calculatorapp.simpelcalculator.ExitButton

@Composable
fun Layout(){
    val context = LocalContext.current
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .background(Color.Blue)
        ) {
            ExitButton(
                label = "Exit",
                modifier = Modifier.padding(16.dp),
                onClick = { (context as Activity).finish() }
            )
        }
        CalculatorWithTabs()
    }
}