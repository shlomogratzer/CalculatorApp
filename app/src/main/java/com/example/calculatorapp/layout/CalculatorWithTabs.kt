package com.example.calculatorapp.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.calculatorapp.loancalculator.LoanCalculatorScreen
import com.example.calculatorapp.simpelcalculator.CalculatorApp

@Composable
fun CalculatorWithTabs() {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("מחשבון רגיל", "מחשבון הלוואות")

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = title,
                            color = if (selectedTabIndex == index)
                                MaterialTheme.colorScheme.onPrimary
                            else
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }
                )
            }
        }

        when (selectedTabIndex) {
            0 -> CalculatorApp()  // המחשבון הרגיל שלך
            1 -> LoanCalculatorScreen()  // מחשבון הלוואות
        }
    }
}
