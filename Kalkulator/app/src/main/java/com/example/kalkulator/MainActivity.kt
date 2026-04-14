package com.example.kalkulator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kalkulator.ui.theme.KalkulatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KalkulatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CalculatorScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CalculatorScreen(modifier: Modifier = Modifier) {
    var number1 by rememberSaveable { mutableStateOf("") }
    var number2 by rememberSaveable { mutableStateOf("") }
    var result by rememberSaveable { mutableStateOf("") }
    var errorMessage by rememberSaveable { mutableStateOf<String?>(null) }

    val invalidNumberMessage = stringResource(R.string.error_invalid_number)
    val divideByZeroMessage = stringResource(R.string.error_divide_by_zero)
    val invalidOperatorMessage = stringResource(R.string.error_invalid_operator)

    fun runCalculation(operator: String) {
        val first = parseNumber(number1)
        val second = parseNumber(number2)

        if (first == null || second == null) {
            errorMessage = invalidNumberMessage
            return
        }

        val calculation = when (operator) {
            "+" -> first + second
            "-" -> first - second
            "*" -> first * second
            "/" -> {
                if (second == 0.0) {
                    errorMessage = divideByZeroMessage
                    return
                }
                first / second
            }
            else -> {
                errorMessage = invalidOperatorMessage
                return
            }
        }

        errorMessage = null
        result = formatResult(calculation)
    }

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .imePadding()
            .navigationBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = number1,
            onValueChange = {
                number1 = it
                errorMessage = null
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(R.string.label_first_number)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true
        )

        OutlinedTextField(
            value = number2,
            onValueChange = {
                number2 = it
                errorMessage = null
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(R.string.label_second_number)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true
        )

        OutlinedTextField(
            value = if (result.isBlank()) "-" else result,
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(R.string.label_result_field)) },
            readOnly = true,
            singleLine = true
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("+", "-", "*", "/").forEach { operator ->
                Button(
                    onClick = { runCalculation(operator) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(operator)
                }
            }
        }

        OutlinedButton(
            onClick = {
                number1 = ""
                number2 = ""
                result = ""
                errorMessage = null
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(R.string.action_clear))
        }

        if (errorMessage != null) {
            Text(
                text = errorMessage!!,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

private fun parseNumber(rawValue: String): Double? {
    // Supports comma decimal input from some numeric keyboards/locales.
    val normalized = rawValue.trim().replace(',', '.')
    return normalized.toDoubleOrNull()
}

private fun formatResult(value: Double): String {
    return if (value % 1.0 == 0.0) {
        value.toLong().toString()
    } else {
        value.toString()
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorScreenPreview() {
    KalkulatorTheme {
        CalculatorScreen(modifier = Modifier.padding(16.dp))
    }
}
