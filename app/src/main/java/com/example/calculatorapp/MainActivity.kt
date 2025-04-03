package com.example.calculatorapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*
import com.example.calculatorapp.ui.theme.CalculatorAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculatorApp()
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CalculatorAppTheme {
        Greeting("Android")
    }
}

@Composable
fun CalculatorApp() {
    var display by remember { mutableStateOf("") }

    fun evaluateExpression(expression: String): Double {
        val tokens = expression.replace(" ", "").split("(?<=[-+*/])|(?=[-+*/])".toRegex())
        val numbers = Stack<Double>()
        val operators = Stack<Char>()

        val precedence = mapOf('+' to 1, '-' to 1, '*' to 2, '/' to 2)

        fun applyOperation() {
            if (numbers.size < 2 || operators.isEmpty()) return
            val b = numbers.pop()
            val a = numbers.pop()
            val op = operators.pop()
            numbers.push(
                when (op) {
                    '+' -> a + b
                    '-' -> a - b
                    '*' -> a * b
                    '/' -> a / b
                    else -> throw IllegalArgumentException("Invalid operator")
                }
            )
        }

        for (token in tokens) {
            when {
                token.toDoubleOrNull() != null -> numbers.push(token.toDouble())
                token.length == 1 && token[0] in precedence.keys -> {
                    while (operators.isNotEmpty() && precedence[token[0]]!! <= precedence[operators.peek()]!!) {
                        applyOperation()
                    }
                    operators.push(token[0])
                }
            }
        }

        while (operators.isNotEmpty()) applyOperation()

        return if (numbers.isNotEmpty()) numbers.pop() else throw IllegalArgumentException("Invalid expression")
    }

    fun calculate(expression: String): String {
        return try {
            val result = evaluateExpression(expression)
            if (result % 1 == 0.0) result.toInt().toString() else result.toString()
        } catch (e: Exception) {
            "Error"
        }
    }

    fun onButtonClick(value: String) {
        when (value) {
            "C" -> display = ""
            "=" -> display = calculate(display)
            else -> display += value
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.LightGray),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = display,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .padding(16.dp)
        )

        val buttons = listOf(
            listOf("C", "7", "8", "9", "/"),
            listOf("4", "5", "6", "*"),
            listOf("1", "2", "3", "-"),
            listOf("0", ".", "=", "+")
        )

        buttons.forEach { row ->
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                row.forEach { label ->
                    Button(
                        onClick = { onButtonClick(label) },
                        modifier = Modifier
                            .weight(1f)
                            .padding(4.dp)
                    ) {
                        Text(label, fontSize = 24.sp)
                    }
                }
            }
        }
    }
}