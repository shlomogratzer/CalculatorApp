package com.example.calculatorapp

import android.app.Activity
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.LayoutDirection
import java.util.*
import com.example.calculatorapp.ui.theme.CalculatorAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                CalculatorApp()
            }
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
    val context = LocalContext.current

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
                lastToken.isNotEmpty() && lastToken[0] in precedence.keys && token[0] in precedence.keys -> {
                    display = "Error"
                }
                token.toDoubleOrNull() != null -> numbers.push(token.toDouble())
                token.length == 1 && token[0] in precedence.keys -> {
                    while (operators.isNotEmpty() && precedence[token[0]]!! <= precedence[operators.peek()]!!) {
                        applyOperation()
                    }
                    operators.push(token[0])
                }
            }
            lastToken = token
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
            "←" -> if (display.isNotEmpty()) display = display.dropLast(1)
            "=" -> display = calculate(display)
            else -> {
                val lastChar = display.lastOrNull()
                if (value.length == 1 && value[0] in "+-*/" && lastChar != null && lastChar in "+-*/") {
                    return
                }
                display += value
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
            .background(Color.LightGray),
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
                .background(Color.LightGray) ,
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
                            onClick = { onButtonClick(label) }
                        )
                    }
                }
            }
        }
    }
}