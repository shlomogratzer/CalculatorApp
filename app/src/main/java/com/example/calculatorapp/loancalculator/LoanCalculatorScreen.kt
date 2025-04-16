package com.example.calculatorapp.loancalculator

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoanCalculatorScreen(viewModel: LoanViewModel = remember { LoanViewModel() }) {
    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = viewModel.loanAmount,
            onValueChange = { viewModel.loanAmount = it },
            label = { Text("סכום הלוואה") }
        )
        OutlinedTextField(
            value = viewModel.interestRate,
            onValueChange = { viewModel.interestRate = it },
            label = { Text("ריבית שנתית (%)") }
        )
        OutlinedTextField(
            value = viewModel.loanTerm,
            onValueChange = { viewModel.loanTerm = it },
            label = { Text("מספר חודשים") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { viewModel.calculateEMI() }) {
            Text("חשב החזר חודשי")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "החזר חודשי: ${viewModel.monthlyPayment}")
    }
}
