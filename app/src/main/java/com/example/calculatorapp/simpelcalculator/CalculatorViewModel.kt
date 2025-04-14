package com.example.calculatorapp.simpelcalculator

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel

class CalculatorViewModel: ViewModel() {
    var display by mutableStateOf("")
        private set

    fun onButtonClick(value: String) {
        when (value) {
            "C" -> display = ""
            "←" -> if (display.isNotEmpty()) display = display.dropLast(1)
            "=" -> display = calculate(display)
            "." -> {
                val lastNumber = display.split("+", "-", "*", "/").last()
                if (lastNumber.contains(".")) return // חוסם נקודה כפולה
                if (lastNumber.isEmpty()) {
                    display += "0."
                } else {
                    display += "."
                }
            }
            "-" -> {
                if (display.isEmpty()) return // חוסם מינוס בתחילת הביטוי
                val lastChar = display.lastOrNull()
                if (lastChar != null && lastChar in "+-*/") return // לא מאפשר רצף אופרטורים
                display += "-"
            }
            else -> {
                val lastChar = display.lastOrNull()
                if (value.length == 1 && value[0] in "+-*/" && lastChar != null && lastChar in "+-*/") {
                    return
                }
                // מניעת אפס מוביל במספר חדש
                if (value in "123456789") {
                    val lastToken = display.split("+", "-", "*", "/").last()
                    if (lastToken == "0") {
                        // מחליף את האפס הנוכחי בספרה החדשה
                        display = display.dropLast(1) + value
                        return
                    }
                }
                // לא מאפשר 00
                if (value == "0") {
                    val lastToken = display.split("+", "-", "*", "/").last()
                    if (lastToken == "0") return
                }

                display += value
            }
        }
    }

    private fun calculate(expression: String): String {
        return try {
            val result = evaluateExpression(expression)

            // בודק אם חורג מהמגבלות של Int
            if (result > Int.MAX_VALUE || result < Int.MIN_VALUE) {
                "Overflow"
            } else if (result % 1 == 0.0) {
                result.toInt().toString()
            } else {
                result.toString()
            }
        } catch (e: Exception) {
            "Error"
        }
    }

    private fun evaluateExpression(expression: String): Double {
        val tokens = expression.replace(" ", "").split("(?<=[-+*/])|(?=[-+*/])".toRegex())
        val numbers = ArrayDeque<Double>()
        val operators = ArrayDeque<Char>()
        val precedence = mapOf('+' to 1, '-' to 1, '*' to 2, '/' to 2)

        fun applyOperation() {
            if (numbers.size < 2 || operators.isEmpty()) return
            val b = numbers.removeFirst()
            val a = numbers.removeFirst()
            val op = operators.removeFirst()
            numbers.addFirst(
                when (op) {
                    '+' -> a + b
                    '-' -> a - b
                    '*' -> a * b
                    '/' -> {
                        if (b == 0.0) throw ArithmeticException("Division by zero")
                        a / b
                    }
                    else -> throw IllegalArgumentException("Invalid operator")
                }
            )
        }

        var lastToken = ""
        for (token in tokens) {
            when {
                lastToken.isNotEmpty() && lastToken[0] in precedence.keys && token[0] in precedence.keys ->
                    throw IllegalArgumentException("Invalid operator sequence")

                token.toDoubleOrNull() != null -> numbers.addFirst(token.toDouble())

                token.length == 1 && token[0] in precedence.keys -> {
                    while (operators.isNotEmpty() && precedence[token[0]]!! <= precedence[operators.first()]!!) {
                        applyOperation()
                    }
                    operators.addFirst(token[0])
                }
            }
            lastToken = token
        }

        while (operators.isNotEmpty()) applyOperation()

        return numbers.removeFirst()
    }
}
