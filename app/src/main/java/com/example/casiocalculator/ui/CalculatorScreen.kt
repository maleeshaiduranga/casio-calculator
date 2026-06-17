package com.example.casiocalculator.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.casiocalculator.AngleMode
import com.example.casiocalculator.CalculatorEvent
import com.example.casiocalculator.CalculatorViewModel
import com.example.casiocalculator.ui.theme.*

@Composable
fun CalculatorScreen(viewModel: CalculatorViewModel = viewModel()) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CalculatorBackground)
            .padding(16.dp)
    ) {
        // Top Brand & Mode Area
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "CASIO",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = TextBlack
            )
            Text(
                text = "fx-991ES PLUS",
                fontSize = 14.sp,
                color = TextBlack,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Display Screen
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(DisplayBackground)
                .padding(12.dp)
        ) {
            // Status indicators
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (state.isShift) StatusIndicator("S", TextBlack)
                if (state.isAlpha) StatusIndicator("A", TextBlack)
                StatusIndicator(state.angleMode.name, TextBlack)
            }
            Spacer(modifier = Modifier.weight(1f))
            // Input string
            Text(
                text = state.input,
                fontSize = 28.sp,
                fontFamily = FontFamily.Monospace,
                color = TextBlack,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Result string
            Text(
                text = state.result,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                color = TextBlack,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
                maxLines = 1
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Keypad Area
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            // Row 1: SHIFT, ALPHA, MODE
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SmallFunctionButton("SHIFT", TextYellow, onClick = { viewModel.onEvent(CalculatorEvent.ToggleShift) })
                SmallFunctionButton("ALPHA", TextPurple, onClick = { viewModel.onEvent(CalculatorEvent.ToggleAlpha) })
                SmallFunctionButton("MODE", TextWhite, onClick = { viewModel.onEvent(CalculatorEvent.ToggleAngleMode) })
            }

            // Row 2: Scientific 1
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                ScientificButton("x⁻¹", shift = "x!", onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("^(-1)")) })
                ScientificButton("√", shift = "³√", onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("√(")) })
                ScientificButton("x²", shift = "³√", onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("^2")) })
                ScientificButton("x^", shift = "x√", onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("^")) })
                ScientificButton("log", shift = "10^", onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("log10(")) })
                ScientificButton("ln", shift = "e^", onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("ln(")) })
            }

            // Row 3: Scientific 2
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                ScientificButton("(-)", shift = "A", alpha = "A", onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("-")) })
                ScientificButton("o'\"", shift = "B", alpha = "B", onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("°")) })
                ScientificButton("hyp", shift = "C", alpha = "C", onClick = { /* Not fully implemented */ })
                ScientificButton("sin", shift = "sin⁻¹", alpha = "D", onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("sin(")) })
                ScientificButton("cos", shift = "cos⁻¹", alpha = "E", onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("cos(")) })
                ScientificButton("tan", shift = "tan⁻¹", alpha = "F", onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("tan(")) })
            }

            // Row 4: Scientific 3
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                ScientificButton("RCL", shift = "STO", onClick = { /* Not fully implemented */ })
                ScientificButton("ENG", shift = "←", onClick = { /* Not fully implemented */ })
                ScientificButton("(", shift = "[", onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("(")) })
                ScientificButton(")", shift = "]", alpha = "X", onClick = { viewModel.onEvent(CalculatorEvent.KeyPress(")")) })
                ScientificButton("S⇔D", onClick = { /* Not fully implemented */ })
                ScientificButton("M+", shift = "M-", alpha = "M", onClick = { /* Not fully implemented */ })
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Numpad Grid
            val numpadSpacing = 12.dp
            Column(verticalArrangement = Arrangement.spacedBy(numpadSpacing)) {
                // Row 1
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(numpadSpacing)) {
                    NumpadButton("7", Modifier.weight(1f), onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("7")) })
                    NumpadButton("8", Modifier.weight(1f), onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("8")) })
                    NumpadButton("9", Modifier.weight(1f), onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("9")) })
                    NumpadButton("DEL", Modifier.weight(1f), color = ButtonBlue, textColor = TextWhite, onClick = { viewModel.onEvent(CalculatorEvent.Delete) })
                    NumpadButton("AC", Modifier.weight(1f), color = ButtonBlue, textColor = TextWhite, onClick = { viewModel.onEvent(CalculatorEvent.Clear) })
                }
                // Row 2
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(numpadSpacing)) {
                    NumpadButton("4", Modifier.weight(1f), onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("4")) })
                    NumpadButton("5", Modifier.weight(1f), onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("5")) })
                    NumpadButton("6", Modifier.weight(1f), onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("6")) })
                    NumpadButton("×", Modifier.weight(1f), onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("×")) })
                    NumpadButton("÷", Modifier.weight(1f), onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("÷")) })
                }
                // Row 3
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(numpadSpacing)) {
                    NumpadButton("1", Modifier.weight(1f), onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("1")) })
                    NumpadButton("2", Modifier.weight(1f), onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("2")) })
                    NumpadButton("3", Modifier.weight(1f), onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("3")) })
                    NumpadButton("+", Modifier.weight(1f), onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("+")) })
                    NumpadButton("-", Modifier.weight(1f), onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("-")) })
                }
                // Row 4
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(numpadSpacing)) {
                    NumpadButton("0", Modifier.weight(1f), onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("0")) })
                    NumpadButton(".", Modifier.weight(1f), onClick = { viewModel.onEvent(CalculatorEvent.KeyPress(".")) })
                    NumpadButton("×10ˣ", Modifier.weight(1f), onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("×10^")) })
                    NumpadButton("Ans", Modifier.weight(1f), onClick = { viewModel.onEvent(CalculatorEvent.KeyPress("Ans")) })
                    NumpadButton("=", Modifier.weight(1f), onClick = { viewModel.onEvent(CalculatorEvent.Calculate) })
                }
            }
        }
    }
}

@Composable
fun StatusIndicator(text: String, color: androidx.compose.ui.graphics.Color) {
    Text(text = text, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = color)
}

@Composable
fun SmallFunctionButton(text: String, textColor: androidx.compose.ui.graphics.Color, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(40.dp, 20.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(ButtonDarkGrey)
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            // Nothing inside the small pill button for these top ones usually, text is around or inside
            // But we'll put text inside for simplicity
            Text(text = text, color = textColor, fontSize = 10.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ScientificButton(
    primary: String,
    shift: String? = null,
    alpha: String? = null,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(50.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = shift ?: "", color = TextYellow, fontSize = 10.sp)
            Text(text = alpha ?: "", color = TextPurple, fontSize = 10.sp)
        }
        Spacer(modifier = Modifier.height(2.dp))
        Box(
            modifier = Modifier
                .size(45.dp, 35.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(ButtonDarkGrey)
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(text = primary, color = TextWhite, fontSize = 14.sp)
        }
    }
}

@Composable
fun NumpadButton(
    text: String,
    modifier: Modifier = Modifier,
    color: androidx.compose.ui.graphics.Color = ButtonLightGrey,
    textColor: androidx.compose.ui.graphics.Color = TextBlack,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .height(55.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(color)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, color = textColor, fontSize = 20.sp, fontWeight = FontWeight.Medium)
    }
}
