package com.example.calculatorapp.layout

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.calculatorapp.loancalculator.LoanCalculatorScreen
import com.example.calculatorapp.simpelcalculator.CalculatorApp
import com.example.calculatorapp.simpelcalculator.ExitButton
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorAppWithDrawer() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var selectedScreen by remember { mutableStateOf("calculator") }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = MaterialTheme.colorScheme.secondary
            ) {
                Text(
                    "תפריט ניווט",
                    modifier = Modifier.padding(30.dp),
                    style = MaterialTheme.typography.headlineSmall
                )
                NavigationDrawerItem(
                    label = { Text("🧮 מחשבון רגיל") },
                    selected = selectedScreen == "calculator",
                    onClick = {
                        selectedScreen = "calculator"
                        scope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
                NavigationDrawerItem(
                    label = { Text("💰 מחשבון הלוואות") },
                    selected = selectedScreen == "loan",
                    onClick = {
                        selectedScreen = "loan"
                        scope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("🧠 אפליקציית מחשבון ") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "תפריט")
                        }
                    },
                    actions = {
                        ExitButton(
                            onClick = { (context as Activity).finish() }
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        ) { innerPadding ->
            Box(modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)) {
                when (selectedScreen) {
                    "calculator" -> CalculatorApp()
                    "loan" -> LoanCalculatorScreen()
                }
            }
        }
    }
}
