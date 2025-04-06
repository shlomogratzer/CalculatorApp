package com.example.calculatorapp

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ExitButton(
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFB71C1C),  // צבע אדום כהה
            contentColor = Color(0xFFF5F5F5)
        ),
        shape = RoundedCornerShape(50), // כפתור עגול יותר
        modifier = modifier
            .height(50.dp)
    ) {
        Text(
            text = label,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun CalculatorButton(
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val isOperator = label in listOf("+", "-", "*", "/")
    val isEquals = label == "="
    val isClear = label == "C"
    val isDelete = label == "←"

    val backgroundColor = when {
        isClear -> Color(0xFFD32F2F)      // אדום
        isDelete -> Color(0xFF757575)     // אפור כהה
        isEquals -> Color(0xFF388E3C)     // ירוק
        isOperator -> Color(0xFF1976D2)   // כחול
        else -> Color(0xFFF5F5F5)         // לבן-אפור (מספרים)
    }

    val contentColor = when {
        isClear || isDelete || isEquals || isOperator -> Color.White
        else -> Color.Black
    }

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .padding(4.dp)
            .height(64.dp)
    ) {
        Text(
            text = label,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
