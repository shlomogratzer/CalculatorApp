package com.example.calculatorapp.loancalculator

import androidx.compose.runtime.*

class LoanViewModel {
    var loanAmount by mutableStateOf("")
    var interestRate by mutableStateOf("")
    var loanTerm by mutableStateOf("")
    var monthlyPayment by mutableStateOf("")

    fun calculateEMI() {
        try {
            val P = loanAmount.toDouble()
            val annualRate = interestRate.toDouble()
            val n = loanTerm.toInt()

            val r = annualRate / 12 / 100
            val emi = (P * r * Math.pow(1 + r, n.toDouble())) / (Math.pow(1 + r, n.toDouble()) - 1)

            monthlyPayment = "%.2f".format(emi)
        } catch (e: Exception) {
            monthlyPayment = "שגיאה"
        }
    }
}
